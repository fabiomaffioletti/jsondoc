package org.jsondoc.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiStatus {
    boolean isPublic() default false;

    boolean isBeta() default false;

    boolean isDeprecated() default false;
}