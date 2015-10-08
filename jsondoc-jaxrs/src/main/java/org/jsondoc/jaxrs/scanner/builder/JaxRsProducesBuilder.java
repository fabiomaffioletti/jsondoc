package org.jsondoc.jaxrs.scanner.builder;

import javax.ws.rs.Produces;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsProducesBuilder {

    public static Set<String> buildProduces(Method method) {
        Set<String> produces = new LinkedHashSet<String>();
        Class<?> controller = method.getDeclaringClass();

        Produces annotation = controller.getAnnotation(Produces.class);
        if (annotation != null) {
            produces.addAll(Arrays.asList(annotation.value()));
        }

        annotation = method.getAnnotation(Produces.class);
        if (annotation != null) {
            produces.addAll(Arrays.asList(annotation.value()));
        }

        return produces;
    }

}
