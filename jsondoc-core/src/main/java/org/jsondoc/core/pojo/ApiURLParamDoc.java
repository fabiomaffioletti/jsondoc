package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiURLParam;


public class ApiURLParamDoc {
	private String name;
	private String description;
	private String type;
	private boolean required;
	private String[] allowedvalues;

	public static ApiURLParamDoc buildFromAnnotation(ApiURLParam annotation) {
		ApiURLParamDoc apiURLParamDoc = new ApiURLParamDoc();
		apiURLParamDoc.setName(annotation.name());
		apiURLParamDoc.setDescription(annotation.description());
		apiURLParamDoc.setRequired(annotation.required());
		apiURLParamDoc.setType(annotation.type());
		apiURLParamDoc.setAllowedvalues(annotation.allowedvalues());
		return apiURLParamDoc;
	}

	public static List<ApiURLParamDoc> buildApiURLParamDocs(Method method) {
		List<ApiURLParamDoc> apiURLParamDocs = new ArrayList<ApiURLParamDoc>();
		for (ApiURLParam apiURLParam : findApiURLParams(method)) {
			apiURLParamDocs.add(ApiURLParamDoc.buildFromAnnotation(apiURLParam));
		}
		return apiURLParamDocs;
	}

	private static List<ApiURLParam> findApiURLParams(Method method) {
		List<ApiURLParam> apiURLparams = new ArrayList<ApiURLParam>();
		Annotation[][] annotations = method.getParameterAnnotations();
		for (Annotation[] innerAllocations : annotations) {
			for (Annotation annotation : innerAllocations) {
				if (annotation instanceof ApiURLParam) {
					apiURLparams.add((ApiURLParam) annotation);
				}
			}
		}
		return apiURLparams;
	}

	public ApiURLParamDoc() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

}
