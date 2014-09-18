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
	public String path();

	/**
	 * The role(s) needed for the method
	 * @return
	 */
	public String role() default "ROLE_ANONYMOUS";

	/**
	 * A description of what the method does
	 * @return
	 */
	public String description();
	
	/**
	 * The request verb (or method), to be filled with an ApiVerb value (GET, POST, PUT, DELETE)
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
	
}
