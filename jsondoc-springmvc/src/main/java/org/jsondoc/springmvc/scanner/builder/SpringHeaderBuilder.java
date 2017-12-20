package org.jsondoc.springmvc.scanner.builder;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.getAnnotation;

public class SpringHeaderBuilder {

	/**
	 * From Spring's documentation: Supported at the type level as well as at
	 * the method level! When used at the type level, all method-level mappings
	 * inherit this header restriction. Finally it looks for @ApiHeader
	 * annotations on parameters and adds the result to the final Set
	 * 
	 * @param method
	 * @return
	 */
	public static Set<ApiHeaderDoc> buildHeaders(Method method) {
		Set<ApiHeaderDoc> headers = new LinkedHashSet<ApiHeaderDoc>();
		Class<?> controller = method.getDeclaringClass();

		RequestMapping typeAnnotation = getAnnotation(controller, RequestMapping.class);
		RequestMapping methodAnnotation = getAnnotation(method, RequestMapping.class);

		if (typeAnnotation != null) {
			List<String> headersStringList = Arrays.asList(typeAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}

		if (methodAnnotation != null) {
			List<String> headersStringList = Arrays.asList(methodAnnotation.headers());
			addToHeaders(headers, headersStringList);
		}
		
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof RequestHeader) {
					RequestHeader requestHeader = (RequestHeader) parametersAnnotations[i][j];
					headers.add(new ApiHeaderDoc(requestHeader.value(), "", requestHeader.defaultValue().equals(ValueConstants.DEFAULT_NONE) ? new String[] {} : new String[] { requestHeader.defaultValue() }));
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

}
