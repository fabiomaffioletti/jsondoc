package org.jsondoc.core.pojo;

import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiResponseObjectDoc {
	private String object;
	private boolean multiple;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation, Method method) {
		return new ApiResponseObjectDoc(annotation.object(), JSONDocUtils.isMultiple(method));
	}
	
	public ApiResponseObjectDoc(String object, boolean multiple) {
		super();
		this.object = object;
		this.multiple = multiple;
	}

	public String getObject() {
		return object;
	}

	public boolean isMultiple() {
		return multiple;
	}

}
