package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;

public class ApiMethodDoc extends AbstractDoc implements Comparable<ApiMethodDoc> {
	public final String jsondocId = UUID.randomUUID().toString();
	private String id;
	private String path;
	private String summary;
	private String description;
	private ApiVerb[] verb;
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
	private String responsestatuscode;
	private MethodDisplay displayMethodAs = MethodDisplay.URI;

	public static ApiMethodDoc buildFromAnnotation(ApiMethod annotation) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setId(annotation.id());
		apiMethodDoc.setPath(annotation.path());
		apiMethodDoc.setSummary(annotation.summary());
		apiMethodDoc.setDescription(annotation.description());
		apiMethodDoc.setVerb(annotation.verb());
		apiMethodDoc.setConsumes(new LinkedHashSet<String>(Arrays.asList(annotation.consumes())));
		apiMethodDoc.setProduces(new LinkedHashSet<String>(Arrays.asList(annotation.produces())));
		apiMethodDoc.setResponsestatuscode(annotation.responsestatuscode());
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

	public ApiVerb[] getVerb() {
		return verb;
	}

	public void setVerb(ApiVerb[] verb) {
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

	public String getResponsestatuscode() {
		return responsestatuscode;
	}

	public void setResponsestatuscode(String responsestatuscode) {
		this.responsestatuscode = responsestatuscode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public MethodDisplay getDisplayMethodAs() {
		return displayMethodAs;
	}

	public void setDisplayMethodAs(MethodDisplay displayMethodAs) {
		this.displayMethodAs = displayMethodAs;
	}

	public String getDisplayedMethodString() {
		if(displayMethodAs.equals(MethodDisplay.URI)) {
			return path;
		} else {
			return summary;
		}
	}

	@Override
	public int compareTo(ApiMethodDoc o) {
		int i = this.path.compareTo(o.getPath());
		if (i != 0)
			return i;

		Arrays.sort(this.verb);
		Arrays.sort(o.getVerb());
		if(Arrays.equals(this.verb, o.getVerb())) {
			i = 0;
		} else {
			i = 1;
		}
		if (i != 0)
			return i;
		
		if(this.queryparameters.size() == o.getQueryparameters().size()) {
			Set<ApiParamDoc> bothQueryParameters = new HashSet<ApiParamDoc>();
			bothQueryParameters.addAll(this.queryparameters);
			bothQueryParameters.addAll(o.getQueryparameters());
			if(bothQueryParameters.size() > this.queryparameters.size()) {
				i = 1;
			}
		} else {
			i = 1;
		}
		
		return i;
	}

}
