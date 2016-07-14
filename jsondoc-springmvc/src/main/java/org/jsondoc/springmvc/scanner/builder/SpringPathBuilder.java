package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			if (pathOrValueAlias(requestMapping).length > 0) {
				controllerMapping = new HashSet<String>(Arrays.asList(pathOrValueAlias(requestMapping)));
			}
		}

		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			if (pathOrValueAlias(requestMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(requestMapping)));
			}
		}

		if (method.isAnnotationPresent(GetMapping.class)) {
			GetMapping getMapping = method.getAnnotation(GetMapping.class);
			if (pathOrValueAlias(getMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(getMapping)));
			}
		}

		if (method.isAnnotationPresent(PostMapping.class)) {
			PostMapping postMapping = method.getAnnotation(PostMapping.class);
			if (pathOrValueAlias(postMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(postMapping)));
			}
		}

		if (method.isAnnotationPresent(PutMapping.class)) {
			PutMapping putMapping = method.getAnnotation(PutMapping.class);
			if (pathOrValueAlias(putMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(putMapping)));
			}
		}

		if (method.isAnnotationPresent(DeleteMapping.class)) {
			DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
			if (pathOrValueAlias(deleteMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(deleteMapping)));
			}
		}

		if (method.isAnnotationPresent(PatchMapping.class)) {
			PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
			if (pathOrValueAlias(patchMapping).length > 0) {
				methodMapping.addAll(Arrays.asList(pathOrValueAlias(patchMapping)));
			}
		}

		// if no path has been specified then adds an empty path to enter the
		// following loop
		if (controllerMapping.isEmpty()) {
			controllerMapping.add("");
		}
		if (methodMapping.isEmpty()) {
			methodMapping.add("");
		}

		for (String controllerPath : controllerMapping) {
			for (String methodPath : methodMapping) {
				paths.add(controllerPath + methodPath);
			}
		}

		for (String path : paths) {
			if (path.equals("")) {
				paths.remove(path);
				paths.add("/");
			}
		}

		return paths;
	}

	// Handle the fact that the method "name" is only in Spring 4.2
	private static String[] pathOrValueAlias(final RequestMapping requestMapping) {
		return StringUtils.isEmpty(requestMapping.value()) ? requestMapping.path() : requestMapping.value();
	}

	private static String[] pathOrValueAlias(final GetMapping getMapping) {
		return StringUtils.isEmpty(getMapping.value()) ? getMapping.path() : getMapping.value();
	}

	private static String[] pathOrValueAlias(final PostMapping postMapping) {
		return StringUtils.isEmpty(postMapping.value()) ? postMapping.path() : postMapping.value();
	}

	private static String[] pathOrValueAlias(final PutMapping putMapping) {
		return StringUtils.isEmpty(putMapping.value()) ? putMapping.path() : putMapping.value();
	}

	private static String[] pathOrValueAlias(final DeleteMapping deleteMapping) {
		return StringUtils.isEmpty(deleteMapping.value()) ? deleteMapping.path() : deleteMapping.value();
	}

	private static String[] pathOrValueAlias(final PatchMapping patchMapping) {
		return StringUtils.isEmpty(patchMapping.value()) ? patchMapping.path() : patchMapping.value();
	}

}
