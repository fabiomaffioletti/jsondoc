package org.jsondoc.core.util;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class JSONDocTemplateBuilder extends AbstractJSONDocTemplateBuilder {

    private static final Logger log = LoggerFactory.getLogger(JSONDocTemplateBuilder.class);

    @Override
    public JSONDocTemplate buildImpl(Class<?> clazz) {
        final JSONDocTemplate jsonDocTemplate = new JSONDocTemplate();
        try {
            Set<JSONDocFieldWrapper> fields = getAllDeclaredFields(clazz);

            for (JSONDocFieldWrapper jsondocFieldWrapper : fields) {
                Field field = jsondocFieldWrapper.getField();
                String fieldName = field.getName();
                ApiObjectField apiObjectField = field.getAnnotation(ApiObjectField.class);
                if (!apiObjectField.name().isEmpty()) {
                    fieldName = apiObjectField.name();
                }

                Object value;
                // This condition is to avoid StackOverflow in case class "A"
                // contains a field of type "A"
                if (field.getType().equals(clazz) || !apiObjectField.processtemplate()) {
                    value = getValue(Object.class);
                } else {
                    value = getValue(field.getType());
                }

                jsonDocTemplate.put(fieldName, value);
            }

        } catch (Exception e) {
            log.error("Error in JSONDocTemplate creation for class [" + clazz.getCanonicalName() + "]", e);
        }

        return jsonDocTemplate;
    }

    private static Set<JSONDocFieldWrapper> getAllDeclaredFields(Class<?> clazz) {
        Set<JSONDocFieldWrapper> fields = new TreeSet<JSONDocFieldWrapper>();
        for (Field field : Arrays.asList(clazz.getDeclaredFields())) {
            if (field.isAnnotationPresent(ApiObjectField.class)) {
                ApiObjectField annotation = field.getAnnotation(ApiObjectField.class);
                fields.add(new JSONDocFieldWrapper(field, annotation.order()));
            }
        }

        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
        }

        return fields;
    }
}
