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
                return mapResponseEntity(method.getGenericReturnType());
            }
            return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), returnType, returnType));
        }
        return null;
    }

    private static ApiResponseObjectDoc mapResponseEntity(Type genericReturnType) {
        Type actualReturnType = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];

        if (actualReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) actualReturnType;

            Class<?> aClass = getClassForType(parameterizedType.getRawType());

            if (Collection.class.isAssignableFrom(aClass)) {
                return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), aClass, actualReturnType));
            }
        }

        Class<?> aClass = getClassForType(actualReturnType);
        return new ApiResponseObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), aClass, aClass));
    }

    private static Class<?> getClassForType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        throw new RuntimeException("Can't determine class for " + type);
    }

}
