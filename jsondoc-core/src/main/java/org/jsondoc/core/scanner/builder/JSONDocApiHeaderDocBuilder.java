package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.pojo.ApiHeaderDoc;

public class JSONDocApiHeaderDocBuilder {
	
	public static Set<ApiHeaderDoc> build(Method method) {
		Set<ApiHeaderDoc> docs = new LinkedHashSet<ApiHeaderDoc>();
		
		ApiHeaders methodAnnotation = method.getAnnotation(ApiHeaders.class);
		ApiHeaders typeAnnotation = method.getDeclaringClass().getAnnotation(ApiHeaders.class);
		
		if(typeAnnotation != null) {
			for (ApiHeader apiHeader : typeAnnotation.headers()) {
				docs.add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description(), apiHeader.allowedvalues()));
			}
		}

		if (methodAnnotation != null) {
			for (ApiHeader apiHeader : methodAnnotation.headers()) {
				docs.add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description(), apiHeader.allowedvalues()));
			}
		}
		
		return docs;
	}
	
}
