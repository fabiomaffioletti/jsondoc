package org.jsondoc.spring.boot.starter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JSONDocConfig.JSONDOC_PROPERTIES_PREFIX)
public class JSONDocProperties {

	private String version;

	private String basePath;

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
