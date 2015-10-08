package org.jsondoc.jaxrs.scanner.builder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class JaxRsPathBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsPathBuilder.class);

    public static Set<String> buildPath(Method method) {
        Class<?> controller = method.getDeclaringClass();

        if (!controller.isAnnotationPresent(Path.class)) {
            throw new IllegalStateException("Jax-RS Pojos must have @Path annotation");
        }

        Path path = controller.getAnnotation(Path.class);
        String controllerMapping = path.value();

        String methodMapping = "";
        if (method.isAnnotationPresent(Path.class)) {
            Path requestMapping = method.getAnnotation(Path.class);
            methodMapping = requestMapping.value();
        }

        String s = "/" + controllerMapping;
        if (StringUtils.isNotBlank(methodMapping)) {
            s += "/" + methodMapping;
        }

        s = StringUtils.replace(s, "//", "/");

        LOGGER.trace("final path: <{}>", s);

        Set<String> ret = new HashSet<String>();
        ret.add(s);
        return ret;
    }
}
