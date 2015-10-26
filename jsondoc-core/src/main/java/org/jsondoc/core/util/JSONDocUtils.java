package org.jsondoc.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiObjectField;

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
	
  public static boolean isFieldMethod(Method method) {
    return (method.getAnnotation(ApiObjectField.class) != null);
  }

  public static String getPropertyName(Method method) {
    String name = method.getName();
    if ((name.startsWith("get") ||  name.startsWith("set")) && name.length() > 3) {
      return name.substring(3, 4).toLowerCase() + name.substring(4);
    } else {
      return name;
    }
  }
}
