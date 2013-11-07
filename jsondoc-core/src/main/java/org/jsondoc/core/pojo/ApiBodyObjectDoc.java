package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiBodyObjectDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String object;
	private String multiple;
	private String mapKeyObject;
	private String mapValueObject;
	private String map;

	public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
		boolean multiple = false;
		Integer index = getApiBodyObjectIndex(method);
		if (index != -1) {
			Class<?> parameter = method.getParameterTypes()[index];
			multiple = JSONDocUtils.isMultiple(parameter);
			return new ApiBodyObjectDoc(getBodyObject(method, index)[0], getBodyObject(method, index)[1], getBodyObject(method, index)[2], String.valueOf(multiple), getBodyObject(method, index)[3]);
		}
		return null;
	}

	private static Integer getApiBodyObjectIndex(Method method) {
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiBodyObject) {
					return i;
				}
			}
		}
		return -1;
	}

	private static String[] getBodyObject(Method method, Integer index) {
		Class<?> parameter = method.getParameterTypes()[index];
		Type generic = method.getGenericParameterTypes()[index];
		if (Map.class.isAssignableFrom(parameter)) {
			Class<?> mapKeyClazz = null;
			Class<?> mapValueClazz = null;

			if (generic instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) generic;
				Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
				Type mapValueType = parameterizedType.getActualTypeArguments()[1];
				mapKeyClazz = (Class<?>) mapKeyType;
				mapValueClazz = (Class<?>) mapValueType;
			}
			return new String[]{JSONDocUtils.getObjectNameFromAnnotatedClass(parameter), (mapKeyClazz != null) ? mapKeyClazz.getSimpleName().toLowerCase() : null, (mapValueClazz != null) ? mapValueClazz.getSimpleName().toLowerCase() : null, "map"};

		} else if (Collection.class.isAssignableFrom(parameter)) {
			if (generic instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) generic;
				Type type = parameterizedType.getActualTypeArguments()[0];
				if(type instanceof WildcardType) {
					return new String[] { JSONDocUtils.WILDCARD, null, null, null };
				}
				Class<?> clazz = (Class<?>) type;
				return new String[]{JSONDocUtils.getObjectNameFromAnnotatedClass(clazz), null, null, null};
			} else {
				return new String[]{JSONDocUtils.UNDEFINED, null, null, null};
			}
		} else if (method.getReturnType().isArray()) {
			Class<?> classArr = parameter;
			return new String[]{JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType()), null, null, null};

		}
		return new String[]{JSONDocUtils.getObjectNameFromAnnotatedClass(parameter), null, null, null};
	}

	public ApiBodyObjectDoc(String object, String mapKeyObject, String mapValueObject, String multiple, String map) {
		super();
		this.object = object;
		this.multiple = multiple;
		this.mapKeyObject = mapKeyObject;
		this.mapValueObject = mapValueObject;
		this.map = map;
	}

	public String getObject() {
		return object;
	}

	public String getMultiple() {
		return multiple;
	}

	public String getMapKeyObject() {
		return mapKeyObject;
	}

	public String getMapValueObject() {
		return mapValueObject;
	}

	public String getMap() {
		return map;
	}

}
