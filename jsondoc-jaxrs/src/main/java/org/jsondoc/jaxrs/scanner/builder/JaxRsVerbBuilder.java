package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.jaxrs.scanner.JaxRsJSONDocScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsVerbBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsVerbBuilder.class);

    public static Set<ApiVerb> buildVerb(Method method) {
        LOGGER.debug("buildVerb: <{}>", method);
        Set<ApiVerb> apiVerbs = new LinkedHashSet<ApiVerb>();

        for (Class m : JaxRsJSONDocScanner.HTTP_METHODS) {
            Annotation annotation = method.getAnnotation(m);
            if (annotation != null) {
                LOGGER.trace("found class <{}>", annotation.annotationType());
                apiVerbs.add(ApiVerb.valueOf(annotation.annotationType().getSimpleName()));
            }
        }

        return apiVerbs;
    }

}
