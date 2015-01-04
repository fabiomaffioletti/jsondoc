package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

public class SpringApiParamDoc {
    private SpringApiParamDoc() {
    }

    public static Set<ApiParamDoc> getPathVariables(Method method) {
        Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

        for (Parameter parameter : method.getParameters()) {
            if (parameter.getAnnotation(PathVariable.class) != null) {
                ApiParamDoc apiParamDoc = buildDocFrom(parameter.getAnnotation(PathVariable.class), parameter);
                docs.add(apiParamDoc);
            }
        }

        return docs;
    }

    public static Set<ApiParamDoc> getRequestParams(Method method) {
        Set<ApiParamDoc> docs = new LinkedHashSet<ApiParamDoc>();

        for (Parameter parameter : method.getParameters()) {
            if (parameter.getAnnotation(RequestParam.class) != null) {
                ApiParamDoc apiParamDoc = buildDocFrom(parameter.getAnnotation(RequestParam.class), parameter);
                docs.add(apiParamDoc);
            }
        }

        return docs;
    }

    private static JSONDocType getJSONDocType(Parameter parameter) {
        Class<?> type = parameter.getType();
        Type genericType = parameter.getType().getGenericSuperclass();
        return JSONDocTypeBuilder.build(new JSONDocType(), type, genericType);
    }

    private static ApiParamDoc buildDocFrom(PathVariable annotation, Parameter parameter) {
        //todo description, allowed values, format
        return new ApiParamDoc(annotation.value(), "", getJSONDocType(parameter), "true", new String[]{}, "");
    }

    private static ApiParamDoc buildDocFrom(RequestParam annotation, Parameter parameter) {
        //todo description, allowed values, format
        return new ApiParamDoc(annotation.value(),
                "",
                getJSONDocType(parameter),
                String.valueOf(annotation.required()),
                new String[]{},
                "");
    }
}
