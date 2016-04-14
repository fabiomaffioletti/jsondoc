package org.jsondoc.core.pojo;

import java.util.Map;
import java.util.Set;

import org.jsondoc.core.pojo.flow.ApiFlowDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;

public class JSONDoc {
	private String version;
	private String basePath;
	// The key is the group these apis belongs to. It can be empty.
	private Map<String, Set<ApiDoc>> apis;
	// The key is the group these objects belongs to. It can be empty.
	private Map<String, Set<ApiObjectDoc>> objects;
	// The key is the group these flows belongs to. It can be empty.
	private Map<String, Set<ApiFlowDoc>> flows;
	private ApiGlobalDoc global;
	private boolean playgroundEnabled;
	private MethodDisplay displayMethodAs;

	public enum MethodDisplay {
		URI, SUMMARY, METHOD;
	}

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

	public Map<String, Set<ApiObjectDoc>> getObjects() {
		return objects;
	}

	public Map<String, Set<ApiDoc>> getApis() {
		return apis;
	}

	public void setApis(Map<String, Set<ApiDoc>> apis) {
		this.apis = apis;
	}

	public void setObjects(Map<String, Set<ApiObjectDoc>> objects) {
		this.objects = objects;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Map<String, Set<ApiFlowDoc>> getFlows() {
		return flows;
	}

	public void setFlows(Map<String, Set<ApiFlowDoc>> flows) {
		this.flows = flows;
	}

	public boolean isPlaygroundEnabled() {
		return playgroundEnabled;
	}

	public void setPlaygroundEnabled(boolean playgroundEnabled) {
		this.playgroundEnabled = playgroundEnabled;
	}

	public MethodDisplay getDisplayMethodAs() {
		return displayMethodAs;
	}

	public void setDisplayMethodAs(MethodDisplay displayMethodAs) {
		this.displayMethodAs = displayMethodAs;
	}

	public ApiGlobalDoc getGlobal() {
		return global;
	}

	public void setGlobal(ApiGlobalDoc global) {
		this.global = global;
	}

	@Override
	public String toString() {
		return "JSONDoc [version=" + version + ", basePath=" + basePath + ", apis=" + apis + ", objects=" + objects + ", flows=" + flows + ", global=" + global + ", playgroundEnabled=" + playgroundEnabled + ", displayMethodAs=" + displayMethodAs + "]";
	}

}
