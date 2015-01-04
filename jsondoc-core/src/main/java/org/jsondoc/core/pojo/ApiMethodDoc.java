package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiMethod;

import java.util.*;

public class ApiMethodDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String path;
	private String description;
	private ApiVerb verb;
	private List<String> produces;
	private List<String> consumes;
	private List<ApiHeaderDoc> headers;
	private Set<ApiParamDoc> pathparameters;
	private Set<ApiParamDoc> queryparameters;
	private ApiBodyObjectDoc bodyobject;
	private ApiResponseObjectDoc response;
	private List<ApiErrorDoc> apierrors;
	private ApiVersionDoc supportedversions;
	private ApiAuthDoc auth;

	public static ApiMethodDoc buildFromAnnotation(ApiMethod annotation) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setPath(annotation.path());
		apiMethodDoc.setDescription(annotation.description());
		apiMethodDoc.setVerb(annotation.verb());
		apiMethodDoc.setConsumes(Arrays.asList(annotation.consumes()));
		apiMethodDoc.setProduces(Arrays.asList(annotation.produces()));
		return apiMethodDoc;
	}

	public ApiMethodDoc() {
		super();
		this.headers = new ArrayList<ApiHeaderDoc>();
		this.pathparameters = new LinkedHashSet<ApiParamDoc>();
		this.queryparameters = new LinkedHashSet<ApiParamDoc>();
		this.apierrors = new ArrayList<ApiErrorDoc>();
	}

	public List<ApiHeaderDoc> getHeaders() {
		return headers;
	}

	public void setHeaders(List<ApiHeaderDoc> headers) {
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

	public ApiVerb getVerb() {
		return verb;
	}

	public void setVerb(ApiVerb verb) {
		this.verb = verb;
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

	public Set<ApiParamDoc> getPathparameters() {
		return pathparameters;
	}

	public void setPathparameters(Set<ApiParamDoc> pathparameters) {
		this.pathparameters = pathparameters;
	}

	public Set<ApiParamDoc> getQueryparameters() {
		return queryparameters;
	}

	public void setQueryparameters(Set<ApiParamDoc> queryparameters) {
		this.queryparameters = queryparameters;
	}

	public ApiResponseObjectDoc getResponse() {
		return response;
	}

	public void setResponse(ApiResponseObjectDoc response) {
		this.response = response;
	}

	public ApiBodyObjectDoc getBodyobject() {
		return bodyobject;
	}

	public void setBodyobject(ApiBodyObjectDoc bodyobject) {
		this.bodyobject = bodyobject;
	}

	public List<ApiErrorDoc> getApierrors() {
		return apierrors;
	}

	public void setApierrors(List<ApiErrorDoc> apierrors) {
		this.apierrors = apierrors;
	}

	public ApiVersionDoc getSupportedversions() {
		return supportedversions;
	}

	public void setSupportedversions(ApiVersionDoc supportedversions) {
		this.supportedversions = supportedversions;
	}

	public ApiAuthDoc getAuth() {
		return auth;
	}

	public void setAuth(ApiAuthDoc auth) {
		this.auth = auth;
	}
}
