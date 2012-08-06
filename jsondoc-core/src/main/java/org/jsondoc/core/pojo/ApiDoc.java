package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.Api;

public class ApiDoc {
	private String path;
	private String description;
	private List<ApiMethodDoc> methods;

	public static ApiDoc buildFromAnnotation(Api api) {
		ApiDoc apiDoc = new ApiDoc();
		apiDoc.setDescription(api.description());
		apiDoc.setPath(api.path());
		return apiDoc;
	}

	public ApiDoc() {
		this.methods = new ArrayList<ApiMethodDoc>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ApiMethodDoc> getMethods() {
		return methods;
	}

	public void setMethods(List<ApiMethodDoc> methods) {
		this.methods = methods;
	}

	public void addMethod(ApiMethodDoc apiMethod) {
		this.methods.add(apiMethod);
	}

}
