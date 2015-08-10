package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.jsondoc.core.pojo.ApiMethodDoc;
import org.springframework.web.bind.annotation.RequestMapping;

public class SpringPathBuilder {

	/**
	 * From Spring's documentation: Supported at the type level as well as at the method level! 
	 * When used at the type level, all method-level mappings inherit this primary mapping, narrowing it 
	 * for a specific handler method.
	 * @param apiMethodDoc
	 * @param method
	 * @param controller
	 * @return
	 */
	public static String buildPath(ApiMethodDoc apiMethodDoc, Method method, Class<?> controller) {
		StringBuffer pathStringBuffer = new StringBuffer();

		if (controller.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = controller.getAnnotation(RequestMapping.class);
			if (requestMapping.value().length > 0) {
				pathStringBuffer.append(requestMapping.value()[0]);
			}
		}

		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			if (requestMapping.value().length > 0) {
				pathStringBuffer.append(requestMapping.value()[0]);
			}
		}
		
		if(pathStringBuffer.length() == 0) {
			pathStringBuffer.append("/");
		}

		return pathStringBuffer.toString();
	}

}
