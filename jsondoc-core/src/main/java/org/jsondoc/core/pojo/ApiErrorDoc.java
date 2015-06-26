package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;

public class ApiErrorDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private String code;
	private String description;

	public static List<ApiErrorDoc> build(Method method) {
		if(method.isAnnotationPresent(ApiErrors.class)) {
			ApiErrors annotation = method.getAnnotation(ApiErrors.class);
			List<ApiErrorDoc> apiMethodDocs = new ArrayList<ApiErrorDoc>();
			for (ApiError apiError : annotation.apierrors()) {
				apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
			}
			return apiMethodDocs;
		}
		
		return new ArrayList<ApiErrorDoc>();
	}

	public ApiErrorDoc(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
