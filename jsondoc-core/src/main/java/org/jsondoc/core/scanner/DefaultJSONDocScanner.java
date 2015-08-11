package org.jsondoc.core.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class DefaultJSONDocScanner extends AbstractJSONDocScanner {
	public static final String UNDEFINED = "undefined";
	public static final String ANONYMOUS = "anonymous";
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		return reflections.getTypesAnnotatedWith(Api.class, true);
	}
	
	@Override
	public Set<Method> jsondocMethods(Class<?> controller) {
		Set<Method> annotatedMethods = new LinkedHashSet<Method>();
		for (Method method : controller.getDeclaredMethods()) {
			if(method.isAnnotationPresent(ApiMethod.class)) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}
	
	@Override
	public Set<Class<?>> jsondocObjects() {
		return reflections.getTypesAnnotatedWith(ApiObject.class, true);
	}

	@Override
	public ApiDoc initApiDoc(Class<?> controller) {
		return ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
	}
	
	@Override
	public ApiMethodDoc initApiMethodDoc(Method method, Class<?> controller) {
		ApiMethodDoc apiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class), controller.getAnnotation(Api.class)); 
		apiMethodDoc.setHeaders(ApiHeaderDoc.build(method));
		apiMethodDoc.setPathparameters(getApiPathParamDocs(method));
		apiMethodDoc.setQueryparameters(getApiQueryParamDocs(method));
		apiMethodDoc.setBodyobject(ApiBodyObjectDoc.build(method));
		apiMethodDoc.setResponse(ApiResponseObjectDoc.build(method));
		return apiMethodDoc;
	}

	@Override
	public ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc) {
		return apiDoc;
	}

	@Override
	public ApiMethodDoc mergeApiMethodDoc(Method method, Class<?> controller, ApiMethodDoc apiMethodDoc) {
		return apiMethodDoc;
	}

	private Set<ApiParamDoc> getApiPathParamDocs(Method method) {
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
	
	private Set<ApiParamDoc> getApiQueryParamDocs(Method method) {
		Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

		if (method.isAnnotationPresent(ApiParams.class)) {
			for (ApiQueryParam apiParam : method.getAnnotation(ApiParams.class).queryparams()) {
				ApiParamDoc apiParamDoc = ApiParamDoc.buildFromAnnotation(apiParam, JSONDocTypeBuilder.build(new JSONDocType(), apiParam.clazz(), apiParam.clazz()), ApiParamType.QUERY);
				docs.add(apiParamDoc);
			}
		}

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiQueryParam) {
					ApiQueryParam annotation = (ApiQueryParam) parametersAnnotations[i][j];
					ApiParamDoc apiParamDoc = ApiParamDoc.buildFromAnnotation(annotation, JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i]), ApiParamType.QUERY);
					docs.add(apiParamDoc);
				}
			}
		}

		return docs;
	}

	@Override
	public ApiObjectDoc initApiObjectDoc(Class<?> clazz) {
		ApiObject annotation = clazz.getAnnotation(ApiObject.class);
		return ApiObjectDoc.buildFromAnnotation(annotation, clazz);
	}

	@Override
	public ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc) {
		ApiObject annotation = clazz.getAnnotation(ApiObject.class);
		if(annotation.show()) {
			return apiObjectDoc;
		} else {
			return null;
		}
	}

}
