package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class ApiParamDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private String name;
	private String description;
	private String required;
	private String[] allowedvalues;
	private String format;

	public ApiParamDoc(String name, String description, JSONDocType jsondocType, String required, String[] allowedvalues, String format) {
		super();
		this.name = name;
		this.description = description;
		this.jsondocType = jsondocType;
		this.required = required;
		this.allowedvalues = allowedvalues;
		this.format = format;
	}

	public static Set<ApiParamDoc> getApiParamDocs(Method method, ApiParamType paramType) {
		Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

		if (method.isAnnotationPresent(ApiParams.class)) {
			for (ApiParam apiParam : method.getAnnotation(ApiParams.class).params()) {
				ApiParamDoc apiParamDoc = buildFromAnnotation(apiParam, JSONDocTypeBuilder.build(new JSONDocType(), apiParam.clazz(), apiParam.clazz()), paramType);
				if (apiParamDoc != null) {
					docs.add(apiParamDoc);
				}
			}
		}

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiParam) {
					ApiParamDoc apiParamDoc = buildFromAnnotation((ApiParam) parametersAnnotations[i][j], getJSONDocType(method, i), paramType);
					if (apiParamDoc != null) {
						docs.add(apiParamDoc);
					}
				}
			}
		}

		return docs;
	}

	protected static JSONDocType getJSONDocType(Method method, Integer index) {
		Class<?> type = method.getParameterTypes()[index];
		Type genericType = method.getGenericParameterTypes()[index];
		return JSONDocTypeBuilder.build(new JSONDocType(), type, genericType);
	}

	public static ApiParamDoc buildFromAnnotation(ApiParam annotation, JSONDocType jsondocType, ApiParamType paramType) {
		if (annotation.paramType().equals(paramType)) {
			return new ApiParamDoc(annotation.name(), annotation.description(), jsondocType, String.valueOf(annotation.required()), annotation.allowedvalues(), annotation.format());
		}
		return null;
	}

	public ApiParamDoc() {
		super();
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRequired() {
		return required;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiParamDoc other = (ApiParamDoc) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
