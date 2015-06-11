package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.util.JSONDocDefaultType;

/**
 * This annotation is to be used inside an annotation of type ApiParams
 * @see ApiParams
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = { ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiPathParam
public @interface ApiQueryParam {

	/**
	 * The name of the url parameter, as expected by the server
	 * @return
	 */
	public String name() default "";

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
	 * The default value for this parameter, if it is not passed in the query string
	 * @return
	 */
	public String defaultvalue() default "";
	
	
	/**
	 * Specify this element if you need to use the ApiParam annotation on the method declaration and not inside the method's signature. This is to be able to document old style servlets'
	 * methods like doGet and doPost. This element, even if specified, is not taken into account when the annotation is put inside the method's signature.
	 * @return
	 */
	public Class<?> clazz() default JSONDocDefaultType.class;
	
}
