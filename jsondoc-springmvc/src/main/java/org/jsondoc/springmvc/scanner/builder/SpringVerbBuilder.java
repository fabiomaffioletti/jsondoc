package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SpringVerbBuilder {

	/**
	 * From Spring's documentation: When [RequestMapping method is] used at the type level, all method-level mappings inherit this HTTP method restriction 
	 * @param method
	 * @param controller
	 * @return
	 */
	public static ApiVerb[] buildVerb(Method method, Class<?> controller) {
		Set<ApiVerb> apiVerbs = new LinkedHashSet<ApiVerb>();
		
		if(controller.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);
			getApiVerbFromRequestMapping(apiVerbs, requestMapping);
		}
		
		if(method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			getApiVerbFromRequestMapping(apiVerbs, requestMapping);
		}
		
		if(apiVerbs.isEmpty()) {
			apiVerbs.add(ApiVerb.GET);
		}
		
		return apiVerbs.toArray(new ApiVerb[apiVerbs.size()]);
	}

	private static void getApiVerbFromRequestMapping(Set<ApiVerb> apiVerbs, RequestMapping requestMapping) {
		if(requestMapping.method().length > 0) {
			for (RequestMethod requestMethod : requestMapping.method()) {
				apiVerbs.add(ApiVerb.valueOf(requestMethod.name()));
			}
		}
	}

}
