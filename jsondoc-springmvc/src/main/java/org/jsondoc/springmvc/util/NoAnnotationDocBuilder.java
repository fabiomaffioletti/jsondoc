package org.jsondoc.springmvc.util;

import org.jsondoc.core.pojo.*;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

public class NoAnnotationDocBuilder extends ApiDoc {

    public static final String UNKNOWN_PARAM_NAME_PREFIX = "[unknownName";
    public static final String DEFAULT_PACKAGE = "default";

    public static ApiResponseObjectDoc buildApiResponseObjectDoc(Method method) {
        return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), method.getReturnType(), method.getGenericReturnType()));
    }

    public static ApiObjectFieldDoc buildApiObjectFieldDoc(Field field) {
        ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
        apiPojoFieldDoc.setName(field.getName());
        apiPojoFieldDoc.setJsondocType(JSONDocTypeBuilder.build(new JSONDocType(), field.getType(), field.getGenericType()));
        apiPojoFieldDoc.setOrder(Integer.MAX_VALUE);
        if (field.getType().getEnumConstants() != null)
            apiPojoFieldDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(field.getType().getEnumConstants()));
        return apiPojoFieldDoc;
    }

    public static ApiObjectDoc buildApiObjectDoc(Class clazz) {
        ApiObjectDoc apiObjectDoc = new ApiObjectDoc();

        Set<ApiObjectFieldDoc> fieldDocs = new TreeSet<ApiObjectFieldDoc>();
        for (Field field : clazz.getDeclaredFields()) {
            ApiObjectFieldDoc fieldDoc = buildApiObjectFieldDoc(field);
            fieldDoc.setSupportedversions(ApiVersionDoc.build(field));
            fieldDocs.add(fieldDoc);
        }

        Class<?> c = clazz.getSuperclass();
        if (c != null) {
            ApiObjectDoc objDoc = buildApiObjectDoc(c);
            fieldDocs.addAll(objDoc.getFields());
        }

        if (clazz.isEnum()) {
            if (clazz.getEnumConstants() != null)
                apiObjectDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(clazz.getEnumConstants()));
        }

        apiObjectDoc.setName(clazz.getSimpleName().toLowerCase());
        apiObjectDoc.setFields(fieldDocs);
        apiObjectDoc.setGroup(getPackageSuffix(clazz));

        apiObjectDoc.setJsondocTemplate(NoAnnotationJSONDocTemplateBuilder.build(clazz));
        return apiObjectDoc;
    }

    public static ApiBodyObjectDoc buildApiRequestBodyDoc(Class parameterType) {
        return new ApiBodyObjectDoc(
                JSONDocTypeBuilder.build(new JSONDocType(), parameterType, parameterType)
        );
    }

    public static ApiParamDoc buildApiRequestParamDoc(Class parameterType, RequestParam requestParam) {
        return new ApiParamDoc(
                requestParam.value(),
                null,
                JSONDocTypeBuilder.build(new JSONDocType(), parameterType, parameterType),
                Boolean.toString(requestParam.required()),
                null,
                null,
                requestParam.defaultValue()
        );
    }

    public static ApiParamDoc buildApiPathParamDoc(Class parameterType, PathVariable pathVariable, int paramIndex) {
        String paramName;
        if (pathVariable.value() == null || pathVariable.value().trim().length() == 0)
            paramName = UNKNOWN_PARAM_NAME_PREFIX + paramIndex + "]";
        else
            paramName = pathVariable.value();

        return new ApiParamDoc(
                paramName,
                null,
                JSONDocTypeBuilder.build(new JSONDocType(), parameterType.getClass(), parameterType.getClass()),
                "true",
                null,
                null,
                null);
    }

    public static ApiDoc buildApiDoc(Class<?> clazz) {
        ApiDoc apiDoc = new ApiDoc();
        apiDoc.setName(clazz.getSimpleName());
        apiDoc.setGroup(getPackageSuffix(clazz));
        return apiDoc;
    }

    public static ApiMethodDoc buildApiMethodDoc(Method method, Class<?> controller) {
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setId(controller.getCanonicalName() + "." + method.getName());
        return apiMethodDoc;
    }

    private static String getPackageSuffix(Class clazz) {
        if (clazz.getPackage() != null && clazz.getPackage().getName() != null
                && clazz.getPackage().getName().trim().length() != 0) {
            String[] tokenizedPackageName = clazz.getPackage().getName().split("[.]");
            return tokenizedPackageName[tokenizedPackageName.length - 1];
        } else {
            return DEFAULT_PACKAGE;
        }
    }
}
