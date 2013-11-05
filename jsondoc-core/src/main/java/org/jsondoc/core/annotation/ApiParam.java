package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiParamType;

/**
 * This annotation is to be used inside an annotation of type ApiParams
 * @see ApiParams
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {

	/**
	 * The name of the url parameter, as expected by the server
	 * @return
	 */
	public String name();

	/**
	 * A description of what the parameter is needed for
	 * @return
	 */
	public String description() default "";
	
	/**
	 * Whether this parameter is required or not. Default value is true
	 * @return
	 */
	public boolean required() default true;
	
	/**
	 * An array representing the allowed values this parameter can have. Default value is *
	 * @return
	 */
	public String[] allowedvalues() default {};
	
	/**
	 * The format from the parameter (ex. yyyy-MM-dd HH:mm:ss, ...)
	 * @return
	 */
	public String format() default "";
	
	/**
	 * Whether this is a path parameter or a query parameter
	 * @return
	 */
	public ApiParamType paramType();
	
}
