package org.jsondoc.springmvc.scanner;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class SpringBuilderUtils {

    public static boolean isAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
        try {
            Class.forName("org.springframework.core.annotation.AnnotatedElementUtils");
            return AnnotatedElementUtils.isAnnotated(clazz, annotation);

        } catch (ClassNotFoundException e) {
            return clazz.isAnnotationPresent(annotation);
        }
    }

    public static boolean isAnnotated(Method method, Class<? extends Annotation> annotation) {
        try {
            Class.forName("org.springframework.core.annotation.AnnotatedElementUtils");
            return AnnotatedElementUtils.isAnnotated(method, annotation);

        } catch (ClassNotFoundException e) {
            return method.isAnnotationPresent(annotation);
        }
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        try {
            Class.forName("org.springframework.core.annotation.AnnotatedElementUtils");
            return AnnotatedElementUtils.getMergedAnnotation(clazz, annotation);

        } catch (ClassNotFoundException e) {
            return clazz.getAnnotation(annotation);
        }
    }

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
        try {
            Class.forName("org.springframework.core.annotation.AnnotatedElementUtils");
            return AnnotatedElementUtils.getMergedAnnotation(method, annotation);

        } catch (ClassNotFoundException e) {
            return method.getAnnotation(annotation);
        }
    }

}
