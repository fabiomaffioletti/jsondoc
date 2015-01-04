package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import java.lang.reflect.Method;

public class SpringApiResponseObjectDoc {

    public static ApiResponseObjectDoc build(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, returnType));
        }
        return null;
    }

}
