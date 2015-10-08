package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.jaxrs.scanner.JaxRsJSONDocScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsVerbBuilder {

    public static Set<ApiVerb> buildVerb(Method method) {
        Set<ApiVerb> apiVerbs = new LinkedHashSet<ApiVerb>();

        for (Class m : JaxRsJSONDocScanner.HTTP_METHODS) {
            Annotation annotation = method.getAnnotation(m);
            if (annotation != null) {
                apiVerbs.add(ApiVerb.valueOf(annotation.getClass().getSimpleName()));
            }

        }

        return apiVerbs;
    }

}
