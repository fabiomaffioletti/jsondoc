package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
	public String name();

	/**
	 * Fully qualified path of the parent class. This has to be specified if your class extends another one and you want to display the object structure in a single shot. Default value is ""
	 * @return
	 */
	public String extendsclass() default "";
	
	/**
	 * Whether to build the json documentation for this object or not. Default value is true
	 * @return
	 */
	public boolean show() default true;
	
}
