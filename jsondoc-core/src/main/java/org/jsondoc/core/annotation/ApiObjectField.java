package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your objects' fields and represents a field of an object
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiObjectField {

	/**
	 * A drescription of what the field is
	 * @return
	 */
	public String description();
	
	/**
	 * The type of the field. This can be a simple type like integer, string, ... or can be an object
	 * @see ApiObject
	 * @return
	 */
	public String type();
	
	/**
	 * Whether this field is a list or not
	 * @return
	 */
	public boolean multiple();

}
