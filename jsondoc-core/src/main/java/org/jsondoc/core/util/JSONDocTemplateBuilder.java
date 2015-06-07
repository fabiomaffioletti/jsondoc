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
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONDocTemplateBuilder {

	private static final Logger log = LoggerFactory.getLogger(JSONDocTemplateBuilder.class);
	private static final Map<Class<?>, Class<?>> primitives = new HashMap<Class<?>, Class<?>>();

	static {
		primitives.put(boolean.class, Boolean.class);
		primitives.put(byte.class, Byte.class);
		primitives.put(char.class, String.class);
		primitives.put(double.class, Double.class);
		primitives.put(float.class, Float.class);
		primitives.put(int.class, Integer.class);
		primitives.put(long.class, Long.class);
		primitives.put(short.class, Short.class);
		primitives.put(void.class, Void.class);
	}

	public static JSONDocTemplate build(Class<?> clazz) {
		final JSONDocTemplate jsonDocTemplate = new JSONDocTemplate();
		try {
			List<Field> fields = getAllDeclaredFields(clazz);
			
			for (Field field : fields) {
				String fieldName = field.getName();
				ApiObjectField apiObjectField = field.getAnnotation(ApiObjectField.class);
				if(!apiObjectField.name().isEmpty()) {
					fieldName = apiObjectField.name();
				}
				
				Object value;
				// This condition is to avoid StackOverflow in case class "A" contains a field of type "A"
				if(field.getType().equals(clazz) || !apiObjectField.processtemplate()) {
					value = getValue(Object.class, field.getGenericType(), fieldName);	
				} else {
					value = getValue(field.getType(), field.getGenericType(), fieldName);
				}

				jsonDocTemplate.put(fieldName, value);
			}

		} catch (Exception e) {
			log.error("Error in JSONDocTemplate creation", e);
		}
		return jsonDocTemplate;
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
			return build(fieldClass);
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
