package org.jsondoc.core.pojo;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class ApiObjectDoc extends AbstractDoc implements Comparable<ApiObjectDoc> {
	public final String jsondocId = UUID.randomUUID().toString();

	private String name;
	private String description;
	private Set<ApiObjectFieldDoc> fields;
	private ApiVersionDoc supportedversions;
	private String[] allowedvalues;
	private String group;
	private ApiVisibility visibility;
	private ApiStage stage;
	private JSONDocTemplate jsondocTemplate;
	private boolean show;

	public ApiObjectDoc() {
		this.name = "";
		this.description = "";
		this.supportedversions = null;
		this.allowedvalues = new String[] {};
		this.fields = new TreeSet<ApiObjectFieldDoc>();
		this.group = "";
		this.visibility = ApiVisibility.UNDEFINED;
		this.stage = ApiStage.UNDEFINED;
		this.jsondocTemplate = null;
		this.show = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ApiObjectFieldDoc> getFields() {
		return fields;
	}

	public void setFields(Set<ApiObjectFieldDoc> fields) {
		this.fields = fields;
	}

	public ApiVersionDoc getSupportedversions() {
		return supportedversions;
	}

	public void setSupportedversions(ApiVersionDoc supportedversions) {
		this.supportedversions = supportedversions;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public JSONDocTemplate getJsondocTemplate() {
		return jsondocTemplate;
	}

	public void setJsondocTemplate(JSONDocTemplate jsondocTemplate) {
		this.jsondocTemplate = jsondocTemplate;
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

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
