package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.ApiVisibility;

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
	 * A custom identifier to be used inside JSONDoc. This string has to be unique inside the JSONDoc documentation.
	 * It's responsibility of the documentation writer to guarantee this uniqueness
	 * @return
	 */
	public String id() default "";

	/**
	 * The relative path for this method (ex. /country/get/{name})
	 * @return
	 */
	public String[] path() default {};

	/**
	 * A summary of what the method does. It's like a short description.
	 * @return
	 */
	public String summary() default "";

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
	public ApiVerb[] verb() default ApiVerb.GET;
	
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
	public String responsestatuscode() default "200 - OK";
	
	/**
	 * Indicates the visibility of the method
	 * @return
	 */
	public ApiVisibility visibility() default ApiVisibility.UNDEFINED;

	/**
	 * Indicates the stage of development or release
	 * @return
	 */
	public ApiStage stage() default ApiStage.UNDEFINED;
	
}
