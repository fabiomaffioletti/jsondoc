package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.util.JSONDocTemplateBuilder;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class ApiBodyObjectDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private Map<String, Object> jsondocTemplate;

	public static ApiBodyObjectDoc build(Method method) {
		if (method.isAnnotationPresent(ApiBodyObject.class)) {
			ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getAnnotation(ApiBodyObject.class).clazz(), method.getAnnotation(ApiBodyObject.class).clazz()));
			apiBodyObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(new HashMap<String, Object>(), method.getAnnotation(ApiBodyObject.class).clazz()));
			return apiBodyObjectDoc;
		}

		Integer index = getApiBodyObjectIndex(method);
		if (index != -1) {
			ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[index], method.getGenericParameterTypes()[index]));
			apiBodyObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(new HashMap<String, Object>(), method.getParameterTypes()[index]));
			return apiBodyObjectDoc;
		}
		return null;
	}

	private static Integer getApiBodyObjectIndex(Method method) {
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiBodyObject) {
					return i;
				}
			}
		}
		return -1;
	}

	public ApiBodyObjectDoc(JSONDocType jsondocType) {
		super();
		this.jsondocType = jsondocType;
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

	public Map<String, Object> getJsondocTemplate() {
		return jsondocTemplate;
	}

	public void setJsondocTemplate(Map<String, Object> jsondocTemplate) {
		this.jsondocTemplate = jsondocTemplate;
	}

	public void setJsondocType(JSONDocType jsondocType) {
		this.jsondocType = jsondocType;
	}

}
