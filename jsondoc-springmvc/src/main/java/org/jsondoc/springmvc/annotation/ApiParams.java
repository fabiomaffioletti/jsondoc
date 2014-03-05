package org.jsondoc.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.annotation.ApiParam;

/**
 * This annotation is to be used on your method and collects the metadata for the method parameter
 * in order to keep the parameter definition as clean as possible.
 * 
 * @see ApiParam
 * @author mcoluzzi
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParams {

	/**
	 * List of {@link ApiParam} to use to better describe the method parameters.
	 * 
	 * @return
	 */
	public ApiParam[] value() default {};

}
