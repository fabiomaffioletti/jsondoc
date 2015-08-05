package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;

public class ApiErrorDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private String code;
	private String description;

	public static List<ApiErrorDoc> build(Method method) {
		List<ApiErrorDoc> apiMethodDocs = new ArrayList<ApiErrorDoc>();

		ApiErrors methodAnnotation = method.getAnnotation(ApiErrors.class);
		ApiErrors typeAnnotation = method.getDeclaringClass().getAnnotation(ApiErrors.class);

		if(methodAnnotation != null) {
			for (ApiError apiError : methodAnnotation.apierrors()) {
				apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
			}
		}

		if(typeAnnotation != null) {
			for (final ApiError apiError : typeAnnotation.apierrors()) {

				boolean isAlreadyDefined = FluentIterable.from(apiMethodDocs).anyMatch(new Predicate<ApiErrorDoc>() {
					@Override
					public boolean apply(ApiErrorDoc apiErrorDoc) {
						return apiError.code().equals(apiErrorDoc.getCode());
					};
				});

				if (!isAlreadyDefined) {
					apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
				}
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

	public String getDescription() {
		return description;
	}

}
