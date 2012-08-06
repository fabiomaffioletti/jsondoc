package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;


public class ApiErrorDoc {
	private String code;
	private String description;

	public static List<ApiErrorDoc> buildFromAnnotation(ApiErrors annotation) {
		List<ApiErrorDoc> apiMethodDocs = new ArrayList<ApiErrorDoc>();
		if (annotation != null) {
			for (ApiError apiError : annotation.apierrors()) {
				apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
			}
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
