package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import org.jsondoc.core.annotation.ApiBodyObject;

public class ApiBodyObjectDoc {
	private String object;
	private boolean multiple;

	public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
		boolean multiple = false;
		Object[] ret = getApiBodyObjectIndex(method);
		if(ret != null) {
			Class<?> parameter = method.getParameterTypes()[(Integer) ret[0]];
			multiple = isMultiple(parameter);
			return new ApiBodyObjectDoc(((ApiBodyObject) ret[1]).object(), multiple);
		}
		return null;
	}
	
	private static Object[] getApiBodyObjectIndex(Method method) {
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for(int i=0; i<parametersAnnotations.length; i++) {
			for(int j=0; j<parametersAnnotations[i].length; j++) {
				if(parametersAnnotations[i][j] instanceof ApiBodyObject) {
					return new Object[]{i, parametersAnnotations[i][j]};
				}
			}
		}
		return null;
	}
	
	private static boolean isMultiple(Class<?> clazz) {
		if(Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
			return true;
		}
		return false;
	}

	public ApiBodyObjectDoc(String object, boolean multiple) {
		super();
		this.object = object;
		this.multiple = multiple;
	}

	public String getObject() {
		return object;
	}

	public boolean isMultiple() {
		return multiple;
	}

}
