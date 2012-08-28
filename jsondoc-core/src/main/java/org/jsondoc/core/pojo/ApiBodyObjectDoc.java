package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiBodyObjectDoc {
	private String object;
	private boolean multiple;

	public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
		boolean multiple = false;
		Object[] ret = getApiBodyObjectIndex(method);
		if(ret != null) {
			Class<?> parameter = method.getParameterTypes()[(Integer) ret[0]];
			multiple = JSONDocUtils.isMultiple(parameter);
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
