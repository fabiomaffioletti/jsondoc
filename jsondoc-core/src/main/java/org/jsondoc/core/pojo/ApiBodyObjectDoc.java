package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiBodyObjectDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String object;
	private String multiple;

	public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
		boolean multiple = false;
		Integer index = getApiBodyObjectIndex(method);
		if(index != -1) {
			Class<?> parameter = method.getParameterTypes()[index];
			multiple = JSONDocUtils.isMultiple(parameter);
			return new ApiBodyObjectDoc(getBodyObject(method, index), String.valueOf(multiple));
		}
		return null;
	}
	
	private static Integer getApiBodyObjectIndex(Method method) {
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for(int i=0; i<parametersAnnotations.length; i++) {
			for(int j=0; j<parametersAnnotations[i].length; j++) {
				if(parametersAnnotations[i][j] instanceof ApiBodyObject) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private static String getBodyObject(Method method, Integer index) {
		Class<?> parameter = method.getParameterTypes()[index];
		Type generic = method.getGenericParameterTypes()[index];
		if(Collection.class.isAssignableFrom(parameter)) {
			if(generic instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) generic;
				Type type = parameterizedType.getActualTypeArguments()[0];
				Class<?> clazz = (Class<?>) type;
				return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz);
			} else {
				return JSONDocUtils.UNDEFINED;
			}
		} else if(method.getReturnType().isArray()) {
			Class<?> classArr = parameter;
			return JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType());
			
		}
		return JSONDocUtils.getObjectNameFromAnnotatedClass(parameter);
	}
	
	public ApiBodyObjectDoc(String object, String multiple) {
		super();
		this.object = object;
		this.multiple = multiple;
	}

	public String getObject() {
		return object;
	}

	public String getMultiple() {
		return multiple;
	}

}
