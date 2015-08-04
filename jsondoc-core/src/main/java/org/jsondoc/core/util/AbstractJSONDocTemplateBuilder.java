package org.jsondoc.core.util;

import org.jsondoc.core.pojo.JSONDocTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJSONDocTemplateBuilder {
    protected static final Map<Class<?>, Class<?>> primitives = new HashMap<Class<?>, Class<?>>();

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

    private static AbstractJSONDocTemplateBuilder templateBuilder;

    private static AbstractJSONDocTemplateBuilder getTemplateBuilder() {
        if (templateBuilder == null)
            return templateBuilder = new JSONDocTemplateBuilder();
        return templateBuilder;
    }

    public static JSONDocTemplate build(Class<?> clazz) {
        return getTemplateBuilder().buildImpl(clazz);
    }

    public abstract JSONDocTemplate buildImpl(Class<?> clazz);

    protected Object getValue(Class<?> fieldClass) {

        if (fieldClass.isPrimitive()) {
            return getValue(wrap(fieldClass));

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
            return buildImpl(fieldClass);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> wrap(Class<T> clazz) {
        return clazz.isPrimitive() ? (Class<T>) primitives.get(clazz) : clazz;
    }
}
