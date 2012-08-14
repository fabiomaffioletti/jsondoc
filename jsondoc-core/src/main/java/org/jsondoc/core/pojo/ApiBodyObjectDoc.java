package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiBodyObject;

public class ApiBodyObjectDoc {
	private String object;
	private String description;
	private boolean multiple;

	public static ApiBodyObjectDoc buildFromAnnotation(ApiBodyObject annotation) {
		return new ApiBodyObjectDoc(annotation.object(),
				annotation.description(), annotation.multiple());
	}

	public ApiBodyObjectDoc(String object, String description, boolean multiple) {
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
