package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpringResponseStatusBuilder {

	public static String buildResponseStatusCode(Method method) {
		if (method.isAnnotationPresent(ResponseStatus.class)) {
			ResponseStatus responseStatus = method.getAnnotation(ResponseStatus.class);
			return responseStatus.value().toString() + " - " + responseStatus.value().getReasonPhrase();
		} else {
			return HttpStatus.OK.toString() + " - " + "OK";
		}
	}

}
