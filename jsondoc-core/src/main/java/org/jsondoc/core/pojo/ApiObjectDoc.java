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
	private String visibility;
	private String stage;
	private String group;
	private JSONDocTemplate jsondocTemplate;

	public ApiObjectDoc() {
		this.name = "";
		this.description = "";
		this.visibility = ApiVisibility.UNDEFINED.getLabel();
		this.stage = ApiStage.UNDEFINED.getLabel();
		this.supportedversions = null;
		this.allowedvalues = new String[]{};
		this.fields = new TreeSet<ApiObjectFieldDoc>();
		this.group = "";
		this.jsondocTemplate = null;
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

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
