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
	
	/**
	 * If the field is required
	 * @return
	 */
	public boolean required() default false;
	
	/**
	 * The display name for this field if different
	 * from the java name
	 * @return
	 */
	public String name() default "";

	/**
	 * Order of field in generated example.
	 * @return
	 */
	public int order() default Integer.MAX_VALUE;

	/**
	 * Whether to process the template for this object or not. It's similar to what can be 
	 * obtained using the JsonIdentityInfo/JsonManagedReference/JsonBackReference. Set this to true
	 * to avoid StackOverflow when your object has a circular or self dependency.
	 * @return
	 */
	public boolean processtemplate() default true; 

}
