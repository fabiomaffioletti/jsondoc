package org.jsondoc.jaxrs.scanner.builder;

import javax.ws.rs.Consumes;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsConsumesBuilder {

    public static Set<String> buildConsumes(Method method) {
        Set<String> consumes = new LinkedHashSet<String>();
        Class<?> controller = method.getDeclaringClass();

        Consumes annotation = controller.getAnnotation(Consumes.class);
        if (annotation != null) {
            consumes.addAll(Arrays.asList(annotation.value()));
        }

        annotation = method.getAnnotation(Consumes.class);
        if (annotation != null) {
            consumes.addAll(Arrays.asList(annotation.value()));
        }

        return consumes;
    }

}
