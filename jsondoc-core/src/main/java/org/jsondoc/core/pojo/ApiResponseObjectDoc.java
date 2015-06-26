package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.util.JSONDocDefaultType;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class ApiResponseObjectDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;

	public static ApiResponseObjectDoc build(Method method) {
		if(method.isAnnotationPresent(ApiResponseObject.class)) {
			ApiResponseObject annotation = method.getAnnotation(ApiResponseObject.class);
			
			if(annotation.clazz().isAssignableFrom(JSONDocDefaultType.class)) {
				return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getReturnType(), method.getGenericReturnType()));
			} else { 
				return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), annotation.clazz(), annotation.clazz()));
			}
		}
		
		return null;
	}

	public ApiResponseObjectDoc(JSONDocType jsondocType) {
		super();
		this.jsondocType = jsondocType;
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

}
