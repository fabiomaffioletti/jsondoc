package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class SpringApiBodyObjectDoc  {
    private SpringApiBodyObjectDoc() {
    }

    public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType().equals(RequestBody.class)) {
                    Class<?> parameterType = parameterTypes[i];
                    return new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), parameterType, parameterType));
                }
            }
        }

   		return null;
   	}

}
