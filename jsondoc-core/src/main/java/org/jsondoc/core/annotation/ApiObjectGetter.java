package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your objects' getter and represents a field of an object
 * 
 * @author mcoluzzi
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiObjectGetter {

	/**
	 * A drescription of what the field is
	 * @return
	 */
	public String description() default "";
	
	/**
	 * The format pattern for this field
	 * @return
	 */
	public String format() default "";
	
	/**
	 * The allowed values for this field
	 * @return
	 */
	public String[] allowedvalues() default {};

}
