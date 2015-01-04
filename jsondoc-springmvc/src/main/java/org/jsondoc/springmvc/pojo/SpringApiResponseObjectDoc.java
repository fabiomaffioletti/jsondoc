package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import java.lang.reflect.Method;
import java.util.Collection;

public class SpringApiResponseObjectDoc {
    private SpringApiResponseObjectDoc() {
    }

    public static ApiResponseObjectDoc build(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            if (Collection.class.isAssignableFrom(returnType)) {
                return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, method.getGenericReturnType()));
            }
            return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, returnType));
        }
        return null;
    }

}
