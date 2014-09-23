package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsondoc.core.annotation.ApiAuthBasic;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiAuthDoc {
	private String type;
	private List<String> roles = new ArrayList<String>();
	private String username;
	private String password;
	
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
		apiAuthDoc.setUsername(annotation.username());
		apiAuthDoc.setPassword(annotation.password());
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
