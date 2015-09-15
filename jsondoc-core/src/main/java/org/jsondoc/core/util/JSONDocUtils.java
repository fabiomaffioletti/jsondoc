package org.jsondoc.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JSONDocUtils {
	
	public static Integer getIndexOfParameterWithAnnotation(Method method, Class<?> a) {
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if(a.equals(parametersAnnotations[i][j].annotationType())) {
					return i;
				}
			}
		}
		return -1;
	}

}
