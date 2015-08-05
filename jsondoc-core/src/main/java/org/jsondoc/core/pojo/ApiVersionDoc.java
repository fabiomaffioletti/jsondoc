package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiVersion;

public class ApiVersionDoc {
	private String since;
	private String until;

	public static ApiVersionDoc build(Class<?> clazz) {
		ApiVersionDoc apiVersionDoc = null;
		if(clazz.isAnnotationPresent(ApiVersion.class)) {
			apiVersionDoc = buildFromAnnotation(clazz.getAnnotation(ApiVersion.class));
		}
		return apiVersionDoc;
	}
	
	/**
	 * In case this annotation is present at type and method level, then the method annotation will override
	 * the type one.
	 */
	public static ApiVersionDoc build(Method method) {
		ApiVersionDoc apiVersionDoc = null;
		ApiVersion methodAnnotation = method.getAnnotation(ApiVersion.class);
		ApiVersion typeAnnotation = method.getDeclaringClass().getAnnotation(ApiVersion.class);
		
		if(typeAnnotation != null) {
			apiVersionDoc = ApiVersionDoc.buildFromAnnotation(typeAnnotation);
		}
		
		if(methodAnnotation != null) {
			apiVersionDoc = buildFromAnnotation(method.getAnnotation(ApiVersion.class));
		}
		
		return apiVersionDoc;
	}
	
	public static ApiVersionDoc build(Field field) {
		ApiVersionDoc apiVersionDoc = null;
		if(field.isAnnotationPresent(ApiVersion.class)) {
			apiVersionDoc = buildFromAnnotation(field.getAnnotation(ApiVersion.class));
		}
		return apiVersionDoc;
	}
	
	private static ApiVersionDoc buildFromAnnotation(ApiVersion annotation) {
		ApiVersionDoc apiVersionDoc = new ApiVersionDoc();
		apiVersionDoc.setSince(annotation.since());
		apiVersionDoc.setUntil(annotation.until());
		return apiVersionDoc;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

}
