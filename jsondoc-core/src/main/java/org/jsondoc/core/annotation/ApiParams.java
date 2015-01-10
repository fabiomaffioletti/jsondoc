package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and contains an array of ApiParam
 * @see ApiParam
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParams {

	/**
	 * An array of ApiQueryParam annotations
	 * @see ApiQueryParam
	 * @return
	 */
	public ApiQueryParam[] queryparams() default {};
	
	/**
	 * An array of ApiPathParam annotations
	 * @see ApiPathParam
	 * @return
	 */
	public ApiPathParam[] pathparams() default {};
	
}
