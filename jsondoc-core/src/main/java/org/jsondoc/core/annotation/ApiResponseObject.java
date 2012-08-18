package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and represents the returned value
 * @see ApiObject
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseObject {

	/**
	 * The name of the object returned by the method.
	 * @see ApiObject
	 * @return
	 */
	public String object();

	/**
	 * A description of what the object contains or represents
	 * @return
	 */
	public String description();
	
	/**
	 * Whether it is a list of objects or a single object
	 * @return
	 */
	public boolean multiple();
	
}
