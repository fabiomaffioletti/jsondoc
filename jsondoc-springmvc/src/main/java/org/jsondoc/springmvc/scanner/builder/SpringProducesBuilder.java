package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;


public class SpringProducesBuilder {

	/**
	 * From Spring's documentation: [produces is] supported at the type level as well as at the method level! 
	 * When used at the type level, all method-level mappings override this produces restriction.
	 * @param method
	 * @param controller
	 * @return
	 */
	public static Set<String> buildProduces(Method method) {
		Set<String> produces = new LinkedHashSet<String>();
		Class<?> controller = method.getDeclaringClass();
		
		if(controller.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);
			if(requestMapping.produces().length > 0) {
				produces.addAll(Arrays.asList(requestMapping.produces()));
			}
		}
		
		if(method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
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
