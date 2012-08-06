package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiRequestBodyObject;


public class ApiRequestBodyObjectDoc {
	private String object;
	private String description;
	private boolean multiple;

	public static ApiRequestBodyObjectDoc buildFromAnnotation(ApiRequestBodyObject annotation) {
		ApiRequestBodyObjectDoc apiObjectDoc = new ApiRequestBodyObjectDoc();
		apiObjectDoc.setDescription(annotation.description());
		apiObjectDoc.setMultiple(annotation.multiple());
		apiObjectDoc.setObject(annotation.object());
		return apiObjectDoc;
	}
	
	public static ApiRequestBodyObjectDoc buildApiRequestBodyObjectDoc(Method method) {
		ApiRequestBodyObject apiRequestBodyObject = findApiRequestBodyObject(method); 
		if(apiRequestBodyObject != null) {
			return ApiRequestBodyObjectDoc.buildFromAnnotation(apiRequestBodyObject);
		}
		return null;
	}
	
	private static ApiRequestBodyObject findApiRequestBodyObject(Method method) {
		Annotation[][] annotations = method.getParameterAnnotations();
		for (Annotation[] innerAllocations : annotations) {
			for (Annotation annotation : innerAllocations) {
				if(annotation instanceof ApiRequestBodyObject) {
					return (ApiRequestBodyObject) annotation;
				}
			}
		}
		return null;
	}

	public ApiRequestBodyObjectDoc() {
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
