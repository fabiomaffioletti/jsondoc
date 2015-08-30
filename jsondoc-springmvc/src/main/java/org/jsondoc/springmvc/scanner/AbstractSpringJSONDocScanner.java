package org.jsondoc.springmvc.scanner;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiFlowSet;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.scanner.AbstractJSONDocScanner;
import org.jsondoc.core.scanner.builder.JSONDocApiDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiMethodDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiObjectDocBuilder;
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
	
	@Override
	public Set<Class<?>> jsondocFlows() {
		return reflections.getTypesAnnotatedWith(ApiFlowSet.class, true);
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
		ApiDoc jsondocApiDoc = JSONDocApiDocBuilder.build(controller);
		BeanUtils.copyProperties(jsondocApiDoc, apiDoc, new String[] { "methods", "supportedversions", "auth" });
		return apiDoc;
	}

	@Override
	public ApiMethodDoc initApiMethodDoc(Method method) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setPath(SpringPathBuilder.buildPath(method));
		apiMethodDoc.setVerb(SpringVerbBuilder.buildVerb(method));
		apiMethodDoc.setProduces(SpringProducesBuilder.buildProduces(method));
		apiMethodDoc.setConsumes(SpringConsumesBuilder.buildConsumes(method));
		apiMethodDoc.setHeaders(SpringHeaderBuilder.buildHeaders(method));
		apiMethodDoc.setPathparameters(SpringPathVariableBuilder.buildPathVariable(method));
		apiMethodDoc.setQueryparameters(SpringQueryParamBuilder.buildQueryParams(method));
		apiMethodDoc.setBodyobject(SpringRequestBodyBuilder.buildRequestBody(method));
		apiMethodDoc.setResponse(SpringResponseBuilder.buildResponse(method));
		apiMethodDoc.setResponsestatuscode(SpringResponseStatusBuilder.buildResponseStatusCode(method));
		return apiMethodDoc;
	}

	@Override
	public ApiMethodDoc mergeApiMethodDoc(Method method, ApiMethodDoc apiMethodDoc) {
		if (method.isAnnotationPresent(ApiMethod.class) && method.getDeclaringClass().isAnnotationPresent(Api.class)) {
			ApiMethodDoc jsondocApiMethodDoc = JSONDocApiMethodDocBuilder.build(method);
			BeanUtils.copyProperties(jsondocApiMethodDoc, apiMethodDoc, new String[] { "path", "verb", "produces", "consumes", "headers", "pathparameters", "queryparameters", "bodyobject", "response", "responsestatuscode", "apierrors", "supportedversions", "auth", "displayMethodAs" });
		}
		return apiMethodDoc;
	}

	@Override
	public ApiObjectDoc initApiObjectDoc(Class<?> clazz) {
		return JSONDocApiObjectDocBuilder.build(clazz);
	}

	@Override
	public ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc) {
		return apiObjectDoc;
	}

	@Override
	public Set<Class<?>> jsondocGlobal() {
		return reflections.getTypesAnnotatedWith(ApiGlobal.class, true);
	}

}
