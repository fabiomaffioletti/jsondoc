package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JaxRsRequestBodyBuilder {

    public static ApiBodyObjectDoc buildRequestBody(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        int i = 0;
        for (Annotation[] a : parameterAnnotations) {
            if (a.length == 0) {
                ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(
                        JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i])
                );
                return apiBodyObjectDoc;
            }
            i++;
        }

        return null;
    }

}
