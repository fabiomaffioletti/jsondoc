package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.getAnnotation;
import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.isAnnotated;

public class SpringResponseStatusBuilder {

	public static String buildResponseStatusCode(Method method) {
		if (isAnnotated(method, ResponseStatus.class)) {
			ResponseStatus responseStatus = getAnnotation(method, ResponseStatus.class);
			return responseStatus.value().toString() + " - " + responseStatus.value().getReasonPhrase();
		} else {
			return HttpStatus.OK.toString() + " - " + "OK";
		}
	}

}
