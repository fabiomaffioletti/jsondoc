package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiObjectField;


public class ApiObjectFieldDoc {
	private String name;
	private String type;
	private boolean multiple;
	private String description;

	public static ApiObjectFieldDoc buildFromAnnotation(ApiObjectField apiPojoField) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		apiPojoFieldDoc.setName(apiPojoField.name());
		apiPojoFieldDoc.setDescription(apiPojoField.description());
		apiPojoFieldDoc.setType(apiPojoField.type());
		apiPojoFieldDoc.setMultiple(apiPojoField.multiple());
		return apiPojoFieldDoc;
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
