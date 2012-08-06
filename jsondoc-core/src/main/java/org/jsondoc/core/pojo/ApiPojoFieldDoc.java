package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiPojoField;


public class ApiPojoFieldDoc {
	private String name;
	private String type;
	private boolean multiple;
	private String description;

	public static ApiPojoFieldDoc buildFromAnnotation(ApiPojoField apiPojoField) {
		ApiPojoFieldDoc apiPojoFieldDoc = new ApiPojoFieldDoc();
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

	public ApiPojoFieldDoc() {
		super();
	}

}
