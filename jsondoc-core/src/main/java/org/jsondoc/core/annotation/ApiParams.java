package org.jsondoc.core.annotation;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: nk
 * Date: 1/23/14
 * Time: 11:50 PM
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParams {

    public ApiParam[] parameters();

}
