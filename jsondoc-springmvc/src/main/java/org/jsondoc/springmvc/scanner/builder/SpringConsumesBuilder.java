package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

public class SpringConsumesBuilder {

	/**
	 * From Spring's documentation: [consumes is] supported at the type level as well as at the method level! 
	 * When used at the type level, all method-level mappings override this consumes restriction.
	 * @param method
	 * @param controller
	 * @return
	 */
	public static Set<String> buildConsumes(Method method, Class<?> controller) {
		Set<String> consumes = new LinkedHashSet<String>();

		if (controller.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);
			if (requestMapping.consumes().length > 0) {
				consumes.addAll(Arrays.asList(requestMapping.consumes()));
			}
		}

		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			if (requestMapping.consumes().length > 0) {
				consumes.clear();
				consumes.addAll(Arrays.asList(requestMapping.consumes()));
			}
		}

		return consumes;
	}

}
