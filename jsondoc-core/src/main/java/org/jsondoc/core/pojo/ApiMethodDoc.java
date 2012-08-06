package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.util.JSONDocMethod;

public class ApiMethodDoc {
	private String path;
	private String description;
	private JSONDocMethod method;
	private List<ApiURLParamDoc> urlparameters;
	private ApiRequestBodyObjectDoc bodyparameter;
	private ApiResponseObjectDoc response;
	private List<ApiErrorDoc> apierrors;

	public static ApiMethodDoc buildFromAnnotation(ApiMethod apiMethod) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setPath(apiMethod.path());
		apiMethodDoc.setDescription(apiMethod.description());
		apiMethodDoc.setMethod(apiMethod.method());
		return apiMethodDoc;
	}

	public ApiMethodDoc() {
		super();
		this.urlparameters = new ArrayList<ApiURLParamDoc>();
	}

	public JSONDocMethod getMethod() {
		return method;
	}

	public void setMethod(JSONDocMethod method) {
		this.method = method;
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

	public List<ApiURLParamDoc> getUrlparameters() {
		return urlparameters;
	}

	public void setUrlparameters(List<ApiURLParamDoc> urlparameters) {
		this.urlparameters = urlparameters;
	}

	public ApiResponseObjectDoc getResponse() {
		return response;
	}

	public void setResponse(ApiResponseObjectDoc response) {
		this.response = response;
	}

	public ApiRequestBodyObjectDoc getBodyparameter() {
		return bodyparameter;
	}

	public void setBodyparameter(ApiRequestBodyObjectDoc bodyparameter) {
		this.bodyparameter = bodyparameter;
	}

	public List<ApiErrorDoc> getApierrors() {
		return apierrors;
	}

	public void setApierrors(List<ApiErrorDoc> apierrors) {
		this.apierrors = apierrors;
	}

}
