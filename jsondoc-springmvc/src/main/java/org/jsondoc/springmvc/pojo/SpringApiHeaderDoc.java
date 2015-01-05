package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpringApiHeaderDoc {
    public static List<ApiHeaderDoc> buildFromAnnotation(Method method) {
        List<ApiHeaderDoc> apiHeaderDocs = new ArrayList<ApiHeaderDoc>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestHeader.class)) {
                    RequestHeader requestHeaderAnnotation = (RequestHeader) annotation;
                    apiHeaderDocs.add(new ApiHeaderDoc(requestHeaderAnnotation.value(), ""));
                }
            }
        }

        return apiHeaderDocs;
    }
}
