package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class SpringApiHeaderDoc {
    public static List<ApiHeaderDoc> buildFromAnnotation(Method method) {
        List<ApiHeaderDoc> apiHeaderDocs = new ArrayList<ApiHeaderDoc>();
        for (Parameter parameter : method.getParameters()) {
            RequestHeader annotation = parameter.getAnnotation(RequestHeader.class);
            if (annotation != null) {
                //todo: support description
                apiHeaderDocs.add(new ApiHeaderDoc(annotation.value(), ""));
            }
        }
        return apiHeaderDocs;
    }
}
