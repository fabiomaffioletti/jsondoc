package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiResponseObject;


public class ApiResponseObjectDoc {
	private String object;
	private String description;
	private boolean multiple;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation) {
		if (annotation != null) {
			ApiResponseObjectDoc apiObjectDoc = new ApiResponseObjectDoc();
			apiObjectDoc.setDescription(annotation.description());
			apiObjectDoc.setMultiple(annotation.multiple());
			apiObjectDoc.setObject(annotation.object());
			return apiObjectDoc;
		} else {
			return null;
		}
	}

	public ApiResponseObjectDoc() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

}
