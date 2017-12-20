package org.jsondoc.springmvc.scanner.builder;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.getAnnotation;
import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.isAnnotated;


public class SpringProducesBuilder {

	/**
	 * From Spring's documentation: [produces is] supported at the type level as well as at the method level! 
	 * When used at the type level, all method-level mappings override this produces restriction.
	 * @param method
	 * @return
	 */
	public static Set<String> buildProduces(Method method) {
		Set<String> produces = new LinkedHashSet<String>();
		Class<?> controller = method.getDeclaringClass();

		if(isAnnotated(controller, RequestMapping.class)) {
			RequestMapping requestMapping = getAnnotation(controller, RequestMapping.class);
			if(requestMapping.produces().length > 0) {
				produces.addAll(Arrays.asList(requestMapping.produces()));
			}
		}
		
		if(isAnnotated(method, RequestMapping.class)) {
			RequestMapping requestMapping = getAnnotation(method, RequestMapping.class);
			if(requestMapping.produces().length > 0) {
				produces.clear();
				produces.addAll(Arrays.asList(requestMapping.produces()));
			}
		}
		
		if(produces.isEmpty()) {
			produces.add(MediaType.APPLICATION_JSON_VALUE);
		}
		
		return produces;
	}

}
