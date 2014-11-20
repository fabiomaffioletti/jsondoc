package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.NullClass;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiResponseObjectDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String object;
	private String multiple;
	private String mapKeyObject;
	private String mapValueObject;
	private String map;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation, Method method) {
		Class<?> returnType;
		if( annotation.value() != NullClass.class ) {
			returnType = annotation.value();
		} else {
			returnType = method.getReturnType();
		}
		return new ApiResponseObjectDoc(getReturnObject(method, returnType)[0], getReturnObject(method, returnType)[1], getReturnObject(method, returnType)[2], String.valueOf(JSONDocUtils.isMultiple(method)), getReturnObject(method, returnType)[3]);
	}

	public static String[] getReturnObject(Method method, Class<?> type) {
		if (Map.class.isAssignableFrom(type)) {
			Class<?> mapKeyClazz = null;
			Class<?> mapValueClazz = null;
			
			if (method.getGenericReturnType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
				Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
				Type mapValueType = parameterizedType.getActualTypeArguments()[1];
				mapKeyClazz = (Class<?>) mapKeyType;
				mapValueClazz = (Class<?>) mapValueType;
			}
			return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(type), (mapKeyClazz != null) ? mapKeyClazz.getSimpleName().toLowerCase() : null, (mapValueClazz != null) ? mapValueClazz.getSimpleName().toLowerCase() : null, "map" };

		} else if (Collection.class.isAssignableFrom(type)) {
			if (method.getGenericReturnType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
				Type actualType = parameterizedType.getActualTypeArguments()[0];
				if(actualType instanceof WildcardType) {
					return new String[] { JSONDocUtils.WILDCARD, null, null, null };
				}
				Class<?> clazz = (Class<?>) actualType;
				return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(clazz), null, null, null };
			} else {
				return new String[] { JSONDocUtils.UNDEFINED, null, null, null };
			}
		} else if (type.isArray()) {
			Class<?> classArr = type;
			return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType()), null, null, null };

		}

		return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(type), null, null, null };
	}

	public ApiResponseObjectDoc(String object, String mapKeyObject, String mapValueObject, String multiple, String map) {
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
