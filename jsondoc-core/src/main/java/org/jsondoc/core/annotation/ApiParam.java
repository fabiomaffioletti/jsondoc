package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiParams
 * @see ApiParams
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.ANNOTATION_TYPE)
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
	public String description();
	
	/**
	 * The type of the parameter (ex. integer, string, ...)
	 * @return
	 */
	public String type();
	
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
	
}
