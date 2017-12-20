package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpringResponseStatusBuilder {

	public static String buildResponseStatusCode(Method method) {
		if (AnnotatedElementUtils.isAnnotated(method, ResponseStatus.class)) {
			ResponseStatus responseStatus = AnnotatedElementUtils.getMergedAnnotation(method, ResponseStatus.class);
			return responseStatus.value().toString() + " - " + responseStatus.value().getReasonPhrase();
		} else {
			return HttpStatus.OK.toString() + " - " + "OK";
		}
	}

}
