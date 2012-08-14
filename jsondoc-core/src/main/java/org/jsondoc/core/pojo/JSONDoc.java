package org.jsondoc.core.pojo;

import java.util.List;

public class JSONDoc {
	private String version;
	private String basePath;
	private List<ApiDoc> apis;
	private List<ApiObjectDoc> objects;

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

	public List<ApiDoc> getApis() {
		return apis;
	}

	public void setApis(List<ApiDoc> apis) {
		this.apis = apis;
	}

	public List<ApiObjectDoc> getObjects() {
		return objects;
	}

	public void setObjects(List<ApiObjectDoc> objects) {
		this.objects = objects;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
