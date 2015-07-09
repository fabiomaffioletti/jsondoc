package org.jsondoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Echos an object string to the output screen.
 * 
 * @goal generate
 * @requiresProject true
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
	private List<String> packages;
	
	/**
	 * @parameter expression="${jsondoc.playgroundEnabled}"
	 * 
	 */
	private boolean playgroundEnabled = true;
	
	/**
	 * @parameter expression="${jsondoc.displayMethodAs}"
	 * 
	 */
	private MethodDisplay displayMethodAs = MethodDisplay.URI;

	/**
	 * @parameter expression="${jsondoc.outputFile}"
	 * @required
	 */
	private String outputFile;
	
	/**
	 * @parameter expression="${jsondoc.scanner}"
	 * @required
	 */
	private String scanner;

    /**
     * @parameter expression="${jsondoc.corsEnabled}"
     *
     */
    private boolean corsEnabled;
	
	private ObjectMapper mapper;
	
	private Log logger;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		mapper = new ObjectMapper();
		logger = getLog();
		
		try {
			List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
			
			URL[] runtimeClasspathElementsUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElementsUrls.length; i++) {
				runtimeClasspathElementsUrls[i] = new File(runtimeClasspathElements.get(i)).toURI().toURL();
			}
			
			URLClassLoader urlClassLoader = new URLClassLoader(runtimeClasspathElementsUrls, Thread.currentThread().getContextClassLoader());
			Thread.currentThread().setContextClassLoader(urlClassLoader);
			
			JSONDocScanner jsondocScanner = (JSONDocScanner) Class.forName(scanner).newInstance();
			JSONDoc apiDoc = jsondocScanner.getJSONDoc(version, basePath, packages, playgroundEnabled, displayMethodAs);
			String jsonApiDoc = mapper.writeValueAsString(apiDoc);
			IOUtils.write(jsonApiDoc, new FileOutputStream(new File(outputFile)));
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
}
