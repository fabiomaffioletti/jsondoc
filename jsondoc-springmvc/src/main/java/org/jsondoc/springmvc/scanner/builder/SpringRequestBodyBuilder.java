package org.jsondoc.springmvc.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocTemplateBuilder;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.RequestBody;

public class SpringRequestBodyBuilder {

	public static ApiBodyObjectDoc buildRequestBody(Method method) {
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
				if (parametersAnnotations[i][j] instanceof RequestBody) {
					return i;
				}
			}
		}
		return -1;
	}
	
}
