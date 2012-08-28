package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;

public class ApiErrorDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String code;
	private String description;

	public static List<ApiErrorDoc> buildFromAnnotation(ApiErrors annotation) {
		List<ApiErrorDoc> apiMethodDocs = new ArrayList<ApiErrorDoc>();
		for (ApiError apiError : annotation.apierrors()) {
			apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
		}
		return apiMethodDocs;
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
