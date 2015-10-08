package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Arne Bosien
 */
public class JaxRsRequestBodyBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsRequestBodyBuilder.class);

    public static ApiBodyObjectDoc buildRequestBody(Method method) {
        int idx = getIndexOfBodyParam(method);

        if (idx != -1) {
            ApiBodyObjectDoc apiBodyObjectDoc = new ApiBodyObjectDoc(
                    JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[idx], method.getGenericParameterTypes()[idx])
            );
            return apiBodyObjectDoc;
        }

        LOGGER.trace("no request body found");
        return null;
    }

    public static int getIndexOfBodyParam(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        int i = 0;
        for (Annotation[] a : parameterAnnotations) {
            if (a.length == 0) {
                LOGGER.trace("found parameter <{}> without annotation", i);
                return i;
            }
            i++;
        }

        LOGGER.trace("no request body found");
        return -1;
    }

}
