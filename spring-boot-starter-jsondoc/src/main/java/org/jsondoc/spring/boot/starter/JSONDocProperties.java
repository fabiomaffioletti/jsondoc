package org.jsondoc.spring.boot.starter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JSONDocConfig.JSONDOC_PROPERTIES_PREFIX)
public class JSONDocProperties {

	/**
	 * The version of your API.
	 */
	private String version;

	/**
	 * The base path of your API, for example http://localhost:8080.
	 */
	private String basePath;

	/**
	 * The list of packages that JSONDoc will scan to look for annotated classes to be documented.
	 */
	private List<String> packages = new ArrayList<String>();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

}
