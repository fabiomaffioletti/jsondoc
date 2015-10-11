package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;

/**
 * This annotation is to be used on your object classes and represents an object used for communication between clients and server
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiObject {

	/**
	 * The name of the object, to be referenced by other annotations with an "object" attribute
	 * @see ApiBodyObject
	 * @see ApiResponseObject
	 * @return
	 */
	public String name() default "";
	
	/**
	 * A description of what the object contains or represents
	 * @return
	 */
	public String description() default "";
	
	/**
	 * Whether to build the json documentation for this object or not. Default value is true
	 * @return
	 */
	public boolean show() default true;
	
	/**
	 * With this it is possible to specify the logical grouping of this object. For example, if you have objects like "city", "country", "continent", "author" and "book", you can say that "city"
	 * "country" and "continent" are grouped in the "Geography" group, and "author" and "book" are grouped in the "Library" group.  
	 * @return
	 */
	public String group() default "";
	
	/**
	 * Indicates the visibility of the object
	 * @return
	 */
	public ApiVisibility visibility() default ApiVisibility.UNDEFINED;

	/**
	 * Indicates the stage of development or release
	 * @return
	 */
	public ApiStage stage() default ApiStage.UNDEFINED;
	
}
