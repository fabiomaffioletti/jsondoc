package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;

import com.google.common.collect.Sets;

public class ApiMethodDoc extends AbstractDoc implements Comparable<ApiMethodDoc> {
	public final String jsondocId = UUID.randomUUID().toString();

	private Set<String> path;
	private String method;
	private Set<ApiVerb> verb;
	private Set<String> produces;
	private Set<String> consumes;
	private Set<ApiHeaderDoc> headers;
	private Set<ApiParamDoc> pathparameters;
	private Set<ApiParamDoc> queryparameters;
	private ApiBodyObjectDoc bodyobject;
	private ApiResponseObjectDoc response;
	private String responsestatuscode;
	private ApiVisibility visibility;
	private ApiStage stage;

	private String id;
	private String description;
	private String summary;

	private List<ApiErrorDoc> apierrors;
	private ApiVersionDoc supportedversions;
	private ApiAuthDoc auth;
	private MethodDisplay displayMethodAs;

	public ApiMethodDoc() {
		super();
		this.id = null;
		this.description = "";
		this.summary = "";
		this.path = new LinkedHashSet<String>();
		this.verb = new LinkedHashSet<ApiVerb>();
		this.produces = new LinkedHashSet<String>();
		this.consumes = new LinkedHashSet<String>();
		this.headers = new LinkedHashSet<ApiHeaderDoc>();
		this.pathparameters = new LinkedHashSet<ApiParamDoc>();
		this.queryparameters = new LinkedHashSet<ApiParamDoc>();
		this.bodyobject = null;
		this.response = null;
		this.responsestatuscode = "";
		this.visibility = ApiVisibility.UNDEFINED;
		this.stage = ApiStage.UNDEFINED;
		this.apierrors = new ArrayList<ApiErrorDoc>();
		this.supportedversions = null;
		this.auth = null;
		this.displayMethodAs = MethodDisplay.URI;
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

	public Set<ApiVerb> getVerb() {
		return verb;
	}

	public void setVerb(Set<ApiVerb> verb) {
		this.verb = verb;
	}

	public Set<String> getPath() {
		return path;
	}

	public void setPath(Set<String> path) {
		this.path = path;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
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

	public Set<String> getDisplayedMethodString() {
		switch (displayMethodAs) {
		case URI:
			return path;
		case SUMMARY:
			return Sets.newHashSet(summary);
		case METHOD:
			return Sets.newHashSet(method);
		default:
			return path;
		}
	}

	public ApiVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(ApiVisibility visibility) {
		this.visibility = visibility;
	}

	public ApiStage getStage() {
		return stage;
	}

	public void setStage(ApiStage stage) {
		this.stage = stage;
	}

	@Override
	public int compareTo(ApiMethodDoc o) {
		int i;
		
		if (this.path.containsAll(o.getPath()) && this.path.size() == o.getPath().size()) {
			i = 0;
		} else {
			i = 1;
		}
		
		if (i != 0)
			return i;

		if (this.verb.containsAll(o.getVerb()) && this.verb.size() == o.getVerb().size()) {
			i = 0;
		} else {
			i = 1;
		}

		if (i != 0)
			return i;

		if (this.queryparameters.size() == o.getQueryparameters().size()) {
			Set<ApiParamDoc> bothQueryParameters = new HashSet<ApiParamDoc>();
			bothQueryParameters.addAll(this.queryparameters);
			bothQueryParameters.addAll(o.getQueryparameters());
			if (bothQueryParameters.size() > this.queryparameters.size()) {
				i = 1;
			}
		} else {
			i = 1;
		}

		return i;
	}

}
