package org.jsondoc.core.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocTemplateBuilder;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class JSONDocApiBodyObjectDocBuilder {

	public static ApiBodyObjectDoc build(Method method) {
		if (method.isAnnotationPresent(ApiBodyObject.class)) {
			ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getAnnotation(ApiBodyObject.class).clazz(), method.getAnnotation(ApiBodyObject.class).clazz()));
			apiBodyObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(method.getAnnotation(ApiBodyObject.class).clazz()));
			return apiBodyObjectDoc;
		}

		Integer index = getApiBodyObjectIndex(method);
		if (index != -1) {
			ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[index], method.getGenericParameterTypes()[index]));
			apiBodyObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(method.getParameterTypes()[index]));
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

}
