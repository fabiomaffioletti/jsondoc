package org.jsondoc.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.annotation.ApiObjectField;

public class JSONDocTemplateBuilder {
	
	private static Map<Class<?>, Class<?>> primitives = new HashMap<Class<?>, Class<?>>();
	
	public static Map<String, Object> build(Map<String, Object> jsondocTemplate, Class<?> clazz) {
		primitives.put(boolean.class, Boolean.class);
		primitives.put(byte.class, Byte.class);
		primitives.put(char.class, String.class);
		primitives.put(double.class, Double.class);
		primitives.put(float.class, Float.class);
		primitives.put(int.class, Integer.class);
		primitives.put(long.class, Long.class);
		primitives.put(short.class, Short.class);
		primitives.put(void.class, Void.class);
		
		try {
			List<Field> fields = getAllDeclaredFields(clazz);
			
			for (Field field : fields) {
				Object value = getValue(field.getType(), field.getGenericType(), field.getName());
				jsondocTemplate.put(field.getName(), value);
			}
			return jsondocTemplate;
			
		} catch (Exception e) {
			return new HashMap<String, Object>();
		}
	}
	
	private static Object getValue(Class<?> fieldClass, Type fieldGenericType, String fieldName) {
		
		if (fieldClass.isPrimitive()) {
			return getValue(wrap(fieldClass), null, fieldName);

		} else if (Map.class.isAssignableFrom(fieldClass)) {
			return new HashMap<Object, Object>();

		} else if (Number.class.isAssignableFrom(fieldClass)) {
			return new Integer(0);

		} else if (String.class.isAssignableFrom(fieldClass) || fieldClass.isEnum()) {
			return new String("");

		} else if (Boolean.class.isAssignableFrom(fieldClass)) {
			return new Boolean("true");

		} else if (fieldClass.isArray() || Collection.class.isAssignableFrom(fieldClass)) {
			return new ArrayList<Object>();

		} else {
			Map<String, Object> template = new HashMap<String, Object>();
			return build(template, fieldClass);
		}
		
	}
	
	private static List<Field> getAllDeclaredFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : Arrays.asList(clazz.getDeclaredFields())) {
			if(field.isAnnotationPresent(ApiObjectField.class)) {
				fields.add(field);
			}
		}
		
		if(clazz.getSuperclass() != null) {
			fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
		}
		
		return fields;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Class<T> wrap(Class<T> clazz) {
		return clazz.isPrimitive() ? (Class<T>) primitives.get(clazz) : clazz;
	}

}
