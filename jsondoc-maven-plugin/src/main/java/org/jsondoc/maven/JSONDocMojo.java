package org.jsondoc.maven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Splitter;

/**
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class JSONDocMojo extends AbstractMojo {

	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * @parameter expression="${jsondoc.version}"
	 * @required
	 */
	private String version;

	/**
	 * @parameter expression="${jsondoc.basePath}"
	 * @required
	 */
	private String basePath;

	/**
	 * @parameter expression="${jsondoc.packages}"
	 * @required
	 */
	private String packages;

	/**
	 * @parameter expression="${jsondoc.packages}"
	 * @required
	 */
	private String outputFile;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		BasicConfigurator.configure(new MavenLoggerLog4jBridge(getLog()));
		Iterable<String> it = Splitter.on(",").omitEmptyStrings().trimResults().split(packages);
		List<String> listPackages = new ArrayList<String>();
		for (String cur : it) {
			listPackages.add(cur);
		}
		JSONDoc result;
		try {
			List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElements.size(); i++) {
				String element = runtimeClasspathElements.get(i);
				runtimeUrls[i] = new File(element).toURI().toURL();
			}

			URLClassLoader newLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
			result = JSONDocUtils.getApiDoc(version, basePath, listPackages, newLoader);
		} catch (Exception e) {
			throw new MojoExecutionException("unable to generate jsondoc", e);
		}
		if (result == null) {
			throw new MojoExecutionException("unable to generate jsondoc");
		}
		File output = new File(outputFile);
		File parentOutput = output.getParentFile();
		if (!parentOutput.exists()) {
			getLog().info("outputFile location doesn't exist: " + parentOutput.getAbsolutePath() + " creating.");
			if (!parentOutput.mkdirs()) {
				throw new MojoExecutionException("unable to create directory for outputFile: " + parentOutput.getAbsolutePath());
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		FileOutputStream fos = null;
		try {
			getLog().info("generating jsondoc to: " + output.getAbsolutePath());
			fos = new FileOutputStream(output);
			mapper.writeValue(fos, result);
		} catch (Exception e) {
			getLog().error("unable to write jsondoc to: " + outputFile, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					getLog().error("unable to close cursors", e);
				}
			}
		}

	}

}
