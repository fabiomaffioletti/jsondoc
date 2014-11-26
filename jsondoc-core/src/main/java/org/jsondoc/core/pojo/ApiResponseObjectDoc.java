package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class ApiResponseObjectDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation, Method method) {
		return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getReturnType(), method.getGenericReturnType()));
	}

	public ApiResponseObjectDoc(JSONDocType jsondocType) {
		super();
		this.jsondocType = jsondocType;
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

}
