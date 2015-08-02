package org.jsondoc.springmvc.util;

import org.jsondoc.core.pojo.JSONDocTemplate;
import org.jsondoc.core.util.AbstractJSONDocTemplateBuilder;
import org.jsondoc.core.util.JSONDocFieldWrapper;
import org.jsondoc.core.util.JSONDocTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class NoAnnotationJSONDocTemplateBuilder extends AbstractJSONDocTemplateBuilder {
    private static final Logger log = LoggerFactory.getLogger(JSONDocTemplateBuilder.class);

    private static class RecursionStateHolder {

        private static final int MAX_RECURSION = 10;
        private static volatile long counter = 0;
        private static volatile Class<?> declaringClazz;

        public static void incrementCounter(Class<?> declaringClazzIn) {
            if(RecursionStateHolder.declaringClazz != null
                    && declaringClazzIn.getCanonicalName().equalsIgnoreCase(RecursionStateHolder.declaringClazz.getCanonicalName())) {
                RecursionStateHolder.counter++;
            } else {
                RecursionStateHolder.declaringClazz = declaringClazzIn;
                counter = 1;
                log.debug("New field [{}]", declaringClazz.getCanonicalName());
            }
        }

        public static boolean isMaxed() {
            return RecursionStateHolder.counter >= MAX_RECURSION;
        }
    }

    @Override
    public JSONDocTemplate buildImpl(Class<?> clazz) {
        final JSONDocTemplate jsonDocTemplate = new JSONDocTemplate();

        try {
            Set<JSONDocFieldWrapper> fields = getAllDeclaredFields(clazz);

            for (JSONDocFieldWrapper jsondocFieldWrapper : fields) {
                Field field = jsondocFieldWrapper.getField();
                String fieldName = field.getName();

                Object value = getValue(field.getType(), clazz);
                jsonDocTemplate.put(fieldName, value);
            }
        } catch (Exception e) {
            log.error("Error in JSONDocTemplate creation for class [{}]", clazz.getCanonicalName(), e);
        }

        return jsonDocTemplate;
    }

    private Object getValue(Class<?> fieldClass, Class<?> declaringClazz) {
        if(RecursionStateHolder.isMaxed()) {
            log.debug("The recursion stack is maxed for [{}]", declaringClazz.getCanonicalName());
            fieldClass = Object.class;
        }
        RecursionStateHolder.incrementCounter(declaringClazz);

        return super.getValue(fieldClass);
    }

    private static Set<JSONDocFieldWrapper> getAllDeclaredFields(Class<?> clazz) {
        Set<JSONDocFieldWrapper> fields = new TreeSet<JSONDocFieldWrapper>();
        for (Field field : Arrays.asList(clazz.getDeclaredFields())) {
            fields.add(new JSONDocFieldWrapper(field, Integer.MAX_VALUE));
        }

        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
        }

        return fields;
    }
}
