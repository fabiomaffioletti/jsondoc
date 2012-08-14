package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiResponseObject;

public class ApiResponseObjectDoc {
	private String object;
	private String description;
	private boolean multiple;

	public static ApiResponseObjectDoc buildFromAnnotation(
			ApiResponseObject annotation) {
		return new ApiResponseObjectDoc(annotation.object(), annotation.description(), annotation.multiple());
	}

	public ApiResponseObjectDoc(String object, String description,
			boolean multiple) {
		super();
		this.object = object;
		this.description = description;
		this.multiple = multiple;
	}

	public String getDescription() {
		return description;
	}

	public String getObject() {
		return object;
	}

	public boolean isMultiple() {
		return multiple;
	}

}
