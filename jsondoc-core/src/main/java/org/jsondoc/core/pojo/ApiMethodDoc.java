package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiMethod;

public class ApiMethodDoc extends AbstractDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String path;
	private String description;
	private ApiVerb verb;
	private Set<String> produces;
	private Set<String> consumes;
	private Set<ApiHeaderDoc> headers;
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
		apiMethodDoc.setConsumes(new LinkedHashSet<String>(Arrays.asList(annotation.consumes())));
		apiMethodDoc.setProduces(new LinkedHashSet<String>(Arrays.asList(annotation.produces())));
		return apiMethodDoc;
	}

	public ApiMethodDoc() {
		super();
		this.headers = new LinkedHashSet<ApiHeaderDoc>();
		this.pathparameters = new LinkedHashSet<ApiParamDoc>();
		this.queryparameters = new LinkedHashSet<ApiParamDoc>();
		this.apierrors = new ArrayList<ApiErrorDoc>();
		this.produces = new LinkedHashSet<String>();
		this.consumes = new LinkedHashSet<String>();
	}

	public Set<ApiHeaderDoc> getHeaders() {
		return headers;
	}

	public void setHeaders(Set<ApiHeaderDoc> headers) {
		this.headers = headers;
	}

	public Set<String> getProduces() {
		return produces;
	}

	public void setProduces(Set<String> produces) {
		this.produces = produces;
	}

	public Set<String> getConsumes() {
		return consumes;
	}

	public void setConsumes(Set<String> consumes) {
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
