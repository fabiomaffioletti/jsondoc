package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiObjectField;

public class ApiObjectFieldDoc {
	private String name;
	private String type;
	private boolean multiple;
	private String description;
	private String format;
	private String[] allowedvalues;

	public static ApiObjectFieldDoc buildFromAnnotation(
			ApiObjectField annotation, String name) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		apiPojoFieldDoc.setName(name);
		apiPojoFieldDoc.setDescription(annotation.description());
		apiPojoFieldDoc.setType(annotation.type());
		apiPojoFieldDoc.setMultiple(annotation.multiple());
		apiPojoFieldDoc.setFormat(annotation.format());
		 apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		return apiPojoFieldDoc;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ApiObjectFieldDoc() {
		super();
	}

}
