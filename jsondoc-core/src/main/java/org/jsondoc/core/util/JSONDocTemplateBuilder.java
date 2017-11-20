package org.jsondoc.core.util;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

	public static JSONDocTemplate build(Class<?> clazz, Set<Class<?>> jsondocObjects) {
		final JSONDocTemplate jsonDocTemplate = new JSONDocTemplate();
        final Map<String, Object> dataWithRootName = new LinkedHashMap<String, Object>();
        Annotation jsonRootName = null;
        try {
            @SuppressWarnings("unchecked")
            final Class<Annotation> jsonRootNameAnnotation = (Class<Annotation>) Class.forName("com.fasterxml.jackson.annotation.JsonRootName");
            final Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                jsonRootName = superclass.getAnnotation(jsonRootNameAnnotation);
            }
            if (jsonRootName == null) {
                jsonRootName = clazz.getAnnotation(jsonRootNameAnnotation);
            }
        } catch (ClassNotFoundException e) {
            log.info("Jackson annotation dependency not met. Support for JsonRootName is not available.");
        }

		if(jsondocObjects.contains(clazz)) {
			try {
				Set<JSONDocFieldWrapper> fields = getAllDeclaredFields(clazz);
	
				for (JSONDocFieldWrapper jsondocFieldWrapper : fields) {
					Field field = jsondocFieldWrapper.getField();
					String fieldName = field.getName();
					ApiObjectField apiObjectField = field.getAnnotation(ApiObjectField.class);
					if (apiObjectField != null && !apiObjectField.name().isEmpty()) {
						fieldName = apiObjectField.name();
					}
	
					Object value;
					// This condition is to avoid StackOverflow in case class "A"
					// contains a field of type "A"
					if (field.getType().equals(clazz) || (apiObjectField != null && !apiObjectField.processtemplate())) {
						value = getValue(Object.class, field.getGenericType(), fieldName, jsondocObjects);
					} else {
						value = getValue(field.getType(), field.getGenericType(), fieldName, jsondocObjects);
					}
	                if (jsonRootName != null) {
                        dataWithRootName.put(fieldName, value);
                    } else {
                        jsonDocTemplate.put(fieldName, value);
                    }
				}
	
			} catch (Exception e) {
				log.error("Error in JSONDocTemplate creation for class [" + clazz.getCanonicalName() + "]", e);
			}
		}
        if (jsonRootName != null) {
            try {
                final Method value = jsonRootName.getClass().getMethod("value");
                jsonDocTemplate.put(value.invoke(jsonRootName).toString(), dataWithRootName);
            } catch (NoSuchMethodException e) {
                log.error("Method 'value' of JsonRootName annotation does not exist. Jackson > 2.0.0 is required");
            } catch (IllegalAccessException e) {
                log.error("Method access of 'value' of JsonRootName annotation is not possible");
            } catch (InvocationTargetException e) {
                log.error("Method 'value' of JsonRootName annotation cannot be invoked");
            }
        }
		return jsonDocTemplate;
	}

	private static Object getValue(Class<?> fieldClass, Type fieldGenericType, String fieldName, Set<Class<?>> jsondocObjects) {

		if (fieldClass.isPrimitive()) {
			return getValue(wrap(fieldClass), null, fieldName, jsondocObjects);

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
			return build(fieldClass, jsondocObjects);
		}

	}

	private static Set<JSONDocFieldWrapper> getAllDeclaredFields(Class<?> clazz) {
		Set<JSONDocFieldWrapper> fields = new TreeSet<JSONDocFieldWrapper>();
		
		List<Field> declaredFields = new ArrayList<Field>();
		if (clazz.isEnum()) {
			return fields;
		} else {
			declaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(ApiObjectField.class)) {
				ApiObjectField annotation = field.getAnnotation(ApiObjectField.class);
				fields.add(new JSONDocFieldWrapper(field, annotation.order()));
			} else {
				fields.add(new JSONDocFieldWrapper(field, Integer.MAX_VALUE));
			}
		}

		if (clazz.getSuperclass() != null) {
			fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
		}

		return fields;
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> wrap(Class<T> clazz) {
		return clazz.isPrimitive() ? (Class<T>) primitives.get(clazz) : clazz;
	}

}
