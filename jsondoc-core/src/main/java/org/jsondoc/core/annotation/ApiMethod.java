package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiVerb;

/**
 * This annotation is to be used on your exposed methods.
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethod {

	/**
	 * The relative path for this method (ex. /country/get/{name})
	 * @return
	 */
	public String path() default "";

	/**
	 * A description of what the method does
	 * @return
	 */
	public String description() default "";
	
	/**
	 * The request verb for this method. Defaults to "GET"
	 * @see ApiVerb
	 * @return
	 */
	public ApiVerb verb() default ApiVerb.GET;
	
	/**
	 * An array of strings representing media types produced by the method, like application/json, application/xml, ...
	 * @return
	 */
	public String[] produces() default {};
	
	/**
	 * An array of strings representing media types consumed by the method, like application/json, application/xml, ...
	 * @return
	 */
	public String[] consumes() default {};
	
	/**
	 * Response status code that this method will return to the caller. Defaults to 200
	 */
	public String responsestatuscode() default "200";
	
}
