package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.ObjectArrays;

public class SpringPathBuilder {

	/**
	 * From Spring's documentation: Supported at the type level as well as at
	 * the method level! When used at the type level, all method-level mappings
	 * inherit this primary mapping, narrowing it for a specific handler method.
	 * 
	 * @param apiMethodDoc
	 * @param method
	 * @param controller
	 * @return
	 */
	public static Set<String> buildPath(Method method) {
		Class<?> controller = method.getDeclaringClass();

		Set<String> paths = new HashSet<String>();
		Set<String> controllerMapping = new HashSet<String>();
		Set<String> methodMapping = new HashSet<String>();

		if (controller.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);
			if (valueMapping(requestMapping).length > 0 || pathMapping(requestMapping).length > 0) {
				controllerMapping = new HashSet<String>(Arrays.asList(ObjectArrays.concat(requestMapping.value(), pathMapping(requestMapping), String.class)));
			}
		}

		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			if (requestMapping.value().length > 0 || pathMapping(requestMapping).length > 0) {
				methodMapping = new HashSet<String>(Arrays.asList(ObjectArrays.concat(requestMapping.value(), pathMapping(requestMapping), String.class)));
			}
		}

		// if no path has been specified then adds an empty path to enter the following loop
		if (controllerMapping.isEmpty()) {
			controllerMapping.add("");
		}
		if(methodMapping.isEmpty()) {
			methodMapping.add("");
		}

		for (String controllerPath : controllerMapping) {
			for (String methodPath : methodMapping) {
				paths.add(controllerPath + methodPath);
			}
		}
		
		for (String path : paths) {
			if(path.equals("")) {
				paths.remove(path);
				paths.add("/");
			}
		}

		return paths;
	}

	//Handle the fact that this method is only in Spring 4, not available in Spring 3
	private static String[] pathMapping(RequestMapping requestMapping) {
		try {
			return requestMapping.path();
		}catch(NoSuchMethodError e) {
			return new String[0];
		}
	}

	private static String[] valueMapping(RequestMapping requestMapping) {
		return requestMapping.value();
	}

}
