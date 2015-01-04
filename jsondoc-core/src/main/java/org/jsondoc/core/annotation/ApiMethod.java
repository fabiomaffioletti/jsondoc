package org.jsondoc.core.annotation;

import org.jsondoc.core.pojo.ApiVerb;

import java.lang.annotation.*;

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
	 * A description of what the method does
	 * @return
	 */
	public String description();
	
	/**
	 * The request verb (or method), to be filled with an ApiVerb value (GET, POST, PUT, DELETE)
	 * @see ApiVerb
	 * @return
	 */
	public ApiVerb verb();
	
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
