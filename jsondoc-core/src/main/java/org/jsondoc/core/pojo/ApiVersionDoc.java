package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiVersion;

public class ApiVersionDoc {
	private String since;
	private String until;

	public static ApiVersionDoc build(Class<?> clazz) {
		if(clazz.isAnnotationPresent(ApiVersion.class)) {
			return buildFromAnnotation(clazz.getAnnotation(ApiVersion.class));
		}
		return null;
	}
	
	public static ApiVersionDoc build(Method method) {
		if(method.isAnnotationPresent(ApiVersion.class)) {
			return buildFromAnnotation(method.getAnnotation(ApiVersion.class));
		}
		return null;
	}
	
	public static ApiVersionDoc build(Field field) {
		if(field.isAnnotationPresent(ApiVersion.class)) {
			return buildFromAnnotation(field.getAnnotation(ApiVersion.class));
		}
		return null;
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
