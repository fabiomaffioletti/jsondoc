package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsondoc.core.annotation.ApiMethod;

public class ApiMethodDoc {
	private String path;
	private String description;
	private ApiVerb method;
	private List<String> headers;
	private List<String> produces;
	private List<String> consumes;
	private List<ApiURLParamDoc> urlparameters;
	private ApiRequestBodyObjectDoc bodyparameter;
	private ApiResponseObjectDoc response;
	private List<ApiErrorDoc> apierrors;

	public static ApiMethodDoc buildFromAnnotation(ApiMethod annotation) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setPath(annotation.path());
		apiMethodDoc.setDescription(annotation.description());
		apiMethodDoc.setMethod(annotation.method());
		apiMethodDoc.setHeaders(Arrays.asList(annotation.headers()));
		apiMethodDoc.setConsumes(Arrays.asList(annotation.consumes()));
		apiMethodDoc.setProduces(Arrays.asList(annotation.produces()));
		return apiMethodDoc;
	}

	public ApiMethodDoc() {
		super();
		this.urlparameters = new ArrayList<ApiURLParamDoc>();
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getProduces() {
		return produces;
	}

	public void setProduces(List<String> produces) {
		this.produces = produces;
	}

	public List<String> getConsumes() {
		return consumes;
	}

	public void setConsumes(List<String> consumes) {
		this.consumes = consumes;
	}

	public ApiVerb getMethod() {
		return method;
	}

	public void setMethod(ApiVerb method) {
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
