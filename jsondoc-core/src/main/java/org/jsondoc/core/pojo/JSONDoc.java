package org.jsondoc.core.pojo;

import java.util.Set;

public class JSONDoc {
	private String version;
	private String basePath;
	private Set<ApiDoc> apis;
	private Set<ApiObjectDoc> objects;

	public JSONDoc(String version, String basePath) {
		super();
		this.version = version;
		this.basePath = basePath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<ApiDoc> getApis() {
		return apis;
	}

	public void setApis(Set<ApiDoc> apis) {
		this.apis = apis;
	}

	public Set<ApiObjectDoc> getObjects() {
		return objects;
	}

	public void setObjects(Set<ApiObjectDoc> objects) {
		this.objects = objects;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
