package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

public class SpringApiParamDoc {
    private SpringApiParamDoc() {
    }

    public static Set<ApiParamDoc> getPathVariables(Method method) {
        Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();
        Class<?>[] parameterTypes = method.getParameterTypes();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(PathVariable.class)) {
                    PathVariable pathVariable = (PathVariable) annotation;
                    ApiParamDoc apiParamDoc = buildDocFrom(pathVariable, parameterTypes[i]);
                    docs.add(apiParamDoc);
                }
            }
        }

        return docs;
    }

    public static Set<ApiParamDoc> getRequestParams(Method method) {
        Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

        Class<?>[] parameterTypes = method.getParameterTypes();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestParam.class)) {
                    RequestParam requestParam = (RequestParam) annotation;
                    ApiParamDoc apiParamDoc = buildDocFrom(requestParam, parameterTypes[i]);
                    docs.add(apiParamDoc);
                }
            }
        }

        return docs;
    }

    private static JSONDocType getJSONDocType(Class<?> parameter) {
        Type genericType = parameter.getGenericSuperclass();
        return JSONDocTypeBuilder.build(new JSONDocType(), parameter, genericType);
    }

    private static ApiParamDoc buildDocFrom(PathVariable annotation, Class<?> parameter) {
        //todo description, allowed values, format
        return new ApiParamDoc(annotation.value(), "", getJSONDocType(parameter), "true", new String[]{}, "");
    }

    private static ApiParamDoc buildDocFrom(RequestParam annotation, Class<?> parameter) {
        //todo description, allowed values, format
        return new ApiParamDoc(annotation.value(),
                "",
                getJSONDocType(parameter),
                String.valueOf(annotation.required()),
                new String[]{},
                "");
    }
}
