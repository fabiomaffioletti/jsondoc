package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiAuthDoc {

	private String type;

	// Basic auth
	private List<String> roles = new ArrayList<String>();
	private Map<String, String> testusers = new HashMap<String, String>();

	// Token auth
	private String scheme;
	private Set<String> testtokens = new HashSet<String>();

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

	public void addRole(String role) {
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

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Set<String> getTesttokens() {
		return testtokens;
	}

	public void setTesttokens(Set<String> testtokens) {
		this.testtokens = testtokens;
	}

	public void addTestToken(String testtoken) {
		this.testtokens.add(testtoken);
	}

}
