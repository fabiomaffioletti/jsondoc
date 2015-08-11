package org.jsondoc.core.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class JSONDocApiPathParameterDocBuilder {
	
	public static Set<ApiParamDoc> build(Method method) {
		Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

		if (method.isAnnotationPresent(ApiParams.class)) {
			for (ApiPathParam apiParam : method.getAnnotation(ApiParams.class).pathparams()) {
				ApiParamDoc apiParamDoc = ApiParamDoc.buildFromAnnotation(apiParam, JSONDocTypeBuilder.build(new JSONDocType(), apiParam.clazz(), apiParam.clazz()), ApiParamType.PATH);
				docs.add(apiParamDoc);
			}
		}

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiPathParam) {
					ApiPathParam annotation = (ApiPathParam) parametersAnnotations[i][j];
					ApiParamDoc apiParamDoc = ApiParamDoc.buildFromAnnotation(annotation, JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i]), ApiParamType.PATH);
					docs.add(apiParamDoc);
				}
			}
		}

		return docs;
	}

}
