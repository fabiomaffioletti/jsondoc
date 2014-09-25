package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.annotation.ApiAuthBasic;
import org.jsondoc.core.annotation.ApiAuthBasicUser;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiAuthDoc {
	private String type;
	private List<String> roles = new ArrayList<String>();
	private Map<String, String> testusers = new HashMap<String, String>();

	public static ApiAuthDoc buildFromUndefined() {
		ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
		apiAuthDoc.setType(JSONDocUtils.UNDEFINED);
		return apiAuthDoc;
	}

	public static ApiAuthDoc buildFromApiAuthNoneAnnotation(ApiAuthNone annotation) {
		ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
		apiAuthDoc.setType(ApiAuthType.NONE.name());
		apiAuthDoc.addRole(JSONDocUtils.ANONYMOUS);
		return apiAuthDoc;
	}

	public static ApiAuthDoc buildFromApiAuthBasicAnnotation(ApiAuthBasic annotation) {
		ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
		apiAuthDoc.setType(ApiAuthType.BASIC_AUTH.name());
		apiAuthDoc.setRoles(Arrays.asList(annotation.roles()));
		for (ApiAuthBasicUser testuser : annotation.testusers()) {
			apiAuthDoc.addTestUser(testuser.username(), testuser.password());
		}
		return apiAuthDoc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	private void addRole(String role) {
		this.roles.add(role);
	}

	public void addTestUser(String username, String password) {
		this.testusers.put(username, password);
	}

	public Map<String, String> getTestusers() {
		return testusers;
	}

	public void setTestusers(Map<String, String> testusers) {
		this.testusers = testusers;
	}

}
