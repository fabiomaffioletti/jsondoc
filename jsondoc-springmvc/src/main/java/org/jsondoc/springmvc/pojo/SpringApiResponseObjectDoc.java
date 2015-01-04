package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class SpringApiResponseObjectDoc {
    private SpringApiResponseObjectDoc() {
    }

    public static ApiResponseObjectDoc build(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            if (Collection.class.isAssignableFrom(returnType)) {
                return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, method.getGenericReturnType()));
            } else if (ResponseEntity.class.equals(returnType)) {
                Type type = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;

                    Class<?> aClass = getClassForType(parameterizedType.getRawType());

                    if (Collection.class.isAssignableFrom(aClass)) {
                        return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), aClass, type));
                    }
                }

                Class<?> aClass = getClassForType(type);
                return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), aClass, aClass));
            }

            return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, returnType));
        }
        return null;
    }

    private static Class<?> getClassForType(Type type) {
        Class<?> aClass = null;
        String className = type.getTypeName();

        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't determine class for " + className, e);
        }
        return aClass;
    }

}
