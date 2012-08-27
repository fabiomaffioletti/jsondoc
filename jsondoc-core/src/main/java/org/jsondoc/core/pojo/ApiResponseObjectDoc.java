package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.Collection;

import org.jsondoc.core.annotation.ApiResponseObject;

public class ApiResponseObjectDoc {
	private String object;
	private boolean multiple;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation, Method method) {
		return new ApiResponseObjectDoc(annotation.object(), isMultiple(method));
	}
	
	private static boolean isMultiple(Method method) {
		if(Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray()) {
			return true;
		}
		return false;
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
