package org.jsondoc.springmvc.scanner;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.scanner.AbstractJSONDocScanner;
import org.jsondoc.springmvc.scanner.builder.SpringConsumesBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringHeaderBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringPathBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringPathVariableBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringProducesBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringQueryParamBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringRequestBodyBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringResponseBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringResponseStatusBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringVerbBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractSpringJSONDocScanner extends AbstractJSONDocScanner {

	@Override
	public Set<Method> jsondocMethods(Class<?> controller) {
		Set<Method> annotatedMethods = new LinkedHashSet<Method>();
		for (Method method : controller.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}

	@Override
	public Set<Class<?>> jsondocObjects() {
		return reflections.getTypesAnnotatedWith(ApiObject.class, true);
	}

	/**
	 * ApiDoc is initialized with the Controller's simple class name.
	 */
	@Override
	public ApiDoc initApiDoc(Class<?> controller) {
		ApiDoc apiDoc = new ApiDoc();
		apiDoc.setName(controller.getSimpleName());
		apiDoc.setDescription(controller.getSimpleName());
		return apiDoc;
	}

	/**
	 * Once the ApiDoc has been initialized and filled with other data (version,
	 * auth, etc) it's time to merge the documentation with JSONDoc annotation,
	 * if existing.
	 */
	@Override
	public ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc) {
		if (controller.isAnnotationPresent(Api.class)) {
			ApiDoc jsondocApiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
			BeanUtils.copyProperties(jsondocApiDoc, apiDoc, new String[] { "methods", "supportedversions", "auth" });
		}
		return apiDoc;
	}

	@Override
	public ApiMethodDoc initApiMethodDoc(Method method, Class<?> controller) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setVerb(SpringVerbBuilder.buildVerb(method, controller));
		apiMethodDoc.setProduces(SpringProducesBuilder.buildProduces(method, controller));
		apiMethodDoc.setConsumes(SpringConsumesBuilder.buildConsumes(method, controller));
		apiMethodDoc.setHeaders(SpringHeaderBuilder.buildHeaders(method, controller));
		apiMethodDoc.setResponse(SpringResponseBuilder.buildResponse(method));
		apiMethodDoc.setResponsestatuscode(SpringResponseStatusBuilder.buildResponseStatusCode(apiMethodDoc, method));
		apiMethodDoc.setPath(SpringPathBuilder.buildPath(apiMethodDoc, method, controller));
		apiMethodDoc.setQueryparameters(SpringQueryParamBuilder.buildQueryParams(method, controller));
		apiMethodDoc.setPathparameters(SpringPathVariableBuilder.buildPathVariable(method, controller));
		apiMethodDoc.setBodyobject(SpringRequestBodyBuilder.buildRequestBody(method));
		return apiMethodDoc;
	}

	@Override
	public ApiMethodDoc mergeApiMethodDoc(Method method, Class<?> controller, ApiMethodDoc apiMethodDoc) {
		if (method.isAnnotationPresent(ApiMethod.class) && controller.isAnnotationPresent(Api.class)) {
			ApiMethodDoc jsondocApiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class), controller.getAnnotation(Api.class));
			BeanUtils.copyProperties(jsondocApiMethodDoc, apiMethodDoc, new String[] { "path", "verb", "produces", "consumes", "headers", "pathparameters", "queryparameters", "bodyobject", "response", "responsestatuscode", "apierrors", "supportedversions", "auth", "displayMethodAs" });
		}
		return apiMethodDoc;
	}

	@Override
	public ApiObjectDoc initApiObjectDoc(Class<?> clazz) {
		ApiObject annotation = clazz.getAnnotation(ApiObject.class);
		return ApiObjectDoc.buildFromAnnotation(annotation, clazz);
	}

	@Override
	public ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc) {
		return apiObjectDoc;
	}

}
