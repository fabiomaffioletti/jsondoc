package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocDefaultType;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class JSONDocApiResponseDocBuilder {

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

}
