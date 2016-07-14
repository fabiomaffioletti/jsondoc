package org.jsondoc.springmvc.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ValueConstants;

public class SpringHeaderBuilder {

	/**
	 * From Spring's documentation: Supported at the type level as well as at
	 * the method level! When used at the type level, all method-level mappings
	 * inherit this header restriction. Finally it looks for @ApiHeader
	 * annotations on parameters and adds the result to the final Set
	 *
	 * @param method
	 * @param controller
	 * @return
	 */
	public static Set<ApiHeaderDoc> buildHeaders(Method method) {
		Set<ApiHeaderDoc> headers = new LinkedHashSet<ApiHeaderDoc>();
		Class<?> controller = method.getDeclaringClass();

		RequestMapping typeAnnotation = controller.getAnnotation(RequestMapping.class);
		RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);

		if (typeAnnotation != null) {
			List<String> headersStringList = Arrays.asList(typeAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		if (methodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(methodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		GetMapping getMethodAnnotation = method.getAnnotation(GetMapping.class);
		if (getMethodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(getMethodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		PostMapping postMethodAnnotation = method.getAnnotation(PostMapping.class);
		if (postMethodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(postMethodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		PutMapping putMethodAnnotation = method.getAnnotation(PutMapping.class);
		if (putMethodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(putMethodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		DeleteMapping deleteMethodAnnotation = method.getAnnotation(DeleteMapping.class);
		if (deleteMethodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(deleteMethodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		PatchMapping patchMethodAnnotation = method.getAnnotation(PatchMapping.class);
		if (deleteMethodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(patchMethodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof RequestHeader) {
					RequestHeader requestHeader = (RequestHeader) parametersAnnotations[i][j];
					headers.add(new ApiHeaderDoc(nameOrValueAlias(requestHeader), "", requestHeader.defaultValue().equals(ValueConstants.DEFAULT_NONE) ? new String[] {} : new String[] { requestHeader.defaultValue() }));
				}
			}
		}

		return headers;
	}

	private static void addToHeaders(Set<ApiHeaderDoc> headers, List<String> headersStringList) {
		for (String header : headersStringList) {
			String[] splitHeader = header.split("=");
			if (splitHeader.length > 1) {
				headers.add(new ApiHeaderDoc(splitHeader[0], null, new String[] { splitHeader[1] }));
			} else {
				headers.add(new ApiHeaderDoc(splitHeader[0], null, new String[] {}));
			}
		}
	}

	// Handle the fact that the method "name" is only in Spring 4.2
	private static String nameOrValueAlias(final RequestHeader requestHeader) {
		return StringUtils.isEmpty(requestHeader.name()) ? requestHeader.value() : requestHeader.name();
	}
}
