package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Method;

import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.http.ResponseEntity;

public class SpringResponseBuilder {

	/**
	 * Builds the ApiResponseObjectDoc from the method's return type and checks if the first type corresponds to a ResponseEntity class. In that case removes the "responseentity"
	 * string from the final list because it's not important to the documentation user.
	 * @param method
	 * @param apiResponseObjectDoc
	 * @return
	 */
	public static ApiResponseObjectDoc buildResponse(Method method) {
		ApiResponseObjectDoc apiResponseObjectDoc = new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getReturnType(), method.getGenericReturnType()));
		
		if(method.getReturnType().isAssignableFrom(ResponseEntity.class)) {
			apiResponseObjectDoc.getJsondocType().getType().remove(0);
		}
		
		return apiResponseObjectDoc;
	}

}
