package org.jsondoc.springmvc.scanner;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class Spring43JSONDocScanner extends Spring4JSONDocScanner {

    private final Class<? extends Annotation>[] HTTP_REQUEST_METHOD_MAPPINGS = new Class[]{
            GetMapping.class,
            PostMapping.class,
            PutMapping.class,
            DeleteMapping.class,
            PatchMapping.class};

    @Override
    public Set<Method> jsondocMethods(Class<?> controller) {
        final Set<Method> methods = super.jsondocMethods(controller);
        for (Method method : controller.getDeclaredMethods()) {
            for (Class<? extends Annotation> clazz : HTTP_REQUEST_METHOD_MAPPINGS) {
                method.isAnnotationPresent(clazz);
            }
        }
        return methods;
    }
}
