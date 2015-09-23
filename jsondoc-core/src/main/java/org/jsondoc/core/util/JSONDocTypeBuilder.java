package org.jsondoc.core.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;

public class JSONDocTypeBuilder {
	
	private static final String WILDCARD = "wildcard";
	private static final String UNDEFINED = "undefined";
	private static final String ARRAY = "array";

	public static JSONDocType build(JSONDocType jsondocType, Class<?> clazz, Type type) {
		if(clazz.isAssignableFrom(JSONDocDefaultType.class)) {
			jsondocType.addItemToType(UNDEFINED);
			return jsondocType;
		}

		if (Map.class.isAssignableFrom(clazz)) {
			jsondocType.addItemToType(getCustomClassName(clazz));

			if (type instanceof ParameterizedType) {
				Type mapKeyType = ((ParameterizedType) type).getActualTypeArguments()[0];
				Type mapValueType = ((ParameterizedType) type).getActualTypeArguments()[1];

				jsondocType.setMapKey(new JSONDocType());
				jsondocType.setMapValue(new JSONDocType());

				if (mapKeyType instanceof Class) {
					jsondocType.setMapKey(new JSONDocType(((Class<?>) mapKeyType).getSimpleName().toLowerCase()));
				} else if (mapKeyType instanceof WildcardType) {
					jsondocType.setMapKey(new JSONDocType(WILDCARD));
				}  else if(mapKeyType instanceof TypeVariable<?>){
					jsondocType.setMapKey(new JSONDocType(((TypeVariable<?>) mapKeyType).getName()));
				} else {
					jsondocType.setMapKey(build(jsondocType.getMapKey(), (Class<?>) ((ParameterizedType) mapKeyType).getRawType(), mapKeyType));
				}

				if (mapValueType instanceof Class) {
					jsondocType.setMapValue(new JSONDocType(((Class<?>) mapValueType).getSimpleName().toLowerCase()));
				} else if (mapValueType instanceof WildcardType) {
					jsondocType.setMapValue(new JSONDocType(WILDCARD));
				} else if(mapValueType instanceof TypeVariable<?>){
					jsondocType.setMapValue(new JSONDocType(((TypeVariable<?>) mapValueType).getName()));
				} else {
					jsondocType.setMapValue(build(jsondocType.getMapValue(), (Class<?>) ((ParameterizedType) mapValueType).getRawType(), mapValueType));
				}

			}

		} else if (Collection.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				Type parametrizedType = ((ParameterizedType) type).getActualTypeArguments()[0];
				jsondocType.addItemToType(getCustomClassName(clazz));
				
				if (parametrizedType instanceof Class) {
					jsondocType.addItemToType(getCustomClassName((Class<?>) parametrizedType));
				} else if (parametrizedType instanceof WildcardType) {
					jsondocType.addItemToType(WILDCARD);
				} else if(parametrizedType instanceof TypeVariable<?>){
					jsondocType.addItemToType(((TypeVariable<?>) parametrizedType).getName());
				} else {
					return build(jsondocType, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType);
				}
			} else if (type instanceof GenericArrayType) {
				return build(jsondocType, clazz, ((GenericArrayType) type).getGenericComponentType());
			} else {
				jsondocType.addItemToType(getCustomClassName(clazz));
			}

		} else if (clazz.isArray()) {
			jsondocType.addItemToType(ARRAY);
			Class<?> componentType = clazz.getComponentType();
			return build(jsondocType, componentType, type);

		} else {
			jsondocType.addItemToType(getCustomClassName(clazz));
			if (type instanceof ParameterizedType) {
				Type parametrizedType = ((ParameterizedType) type).getActualTypeArguments()[0];
				
				if (parametrizedType instanceof Class) {
					jsondocType.addItemToType(getCustomClassName((Class<?>) parametrizedType));
				} else if (parametrizedType instanceof WildcardType) {
					jsondocType.addItemToType(WILDCARD);
				} else if(parametrizedType instanceof TypeVariable<?>){
					jsondocType.addItemToType(((TypeVariable<?>) parametrizedType).getName());
				} else {
					return build(jsondocType, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType);
				}
			}
		}

		return jsondocType;
	}
	
	private static String getCustomClassName(Class<?> clazz) {
		if(clazz.isAnnotationPresent(ApiObject.class)) {
			ApiObject annotation = clazz.getAnnotation(ApiObject.class);
			if(annotation.name().isEmpty()) {
				return clazz.getSimpleName().toLowerCase();
			} else {
				return annotation.name();
			}
		} else {
			return clazz.getSimpleName().toLowerCase();
		}
	}

}