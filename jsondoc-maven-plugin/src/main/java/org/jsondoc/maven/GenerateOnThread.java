package org.jsondoc.maven;

import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;

class GenerateOnThread implements Runnable {

	private final String version;
	private final String basePath;
	private final List<String> listPackages;
	private final ClassLoader newLoader;
	private final Log log;
	private JSONDoc doc;

	public GenerateOnThread(String version, String basePath, List<String> listPackages, ClassLoader newLoader, Log log) {
		this.version = version;
		this.basePath = basePath;
		this.listPackages = listPackages;
		this.newLoader = newLoader;
		this.log = log;
	}

	@Override
	public void run() {
		try {
			doc = JSONDocUtils.getApiDoc(version, basePath, listPackages);
		} catch (Exception e) {
			log.error("unable to execute on thread", e);
		}
	}

	public JSONDoc getDoc() throws Exception {
		Thread t = new Thread(this, "thread");
		t.setContextClassLoader(newLoader);
		t.setDaemon(true);
		t.start();
		t.join();
		return doc;
	}

}
