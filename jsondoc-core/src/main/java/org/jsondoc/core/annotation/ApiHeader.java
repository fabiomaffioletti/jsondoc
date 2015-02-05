package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiHeaders
 * @see ApiHeaders
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = { ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeader {

	/**
	 * The name of the header parameter
	 * @return
	 */
	public String name() default "";

	/**
	 * A description of what the header is needed for
	 * @return
	 */
	public String description() default "";
	
	/**
	 * An array representing the allowed values this header can have. Default value is *
	 * @return
	 */
	public String[] allowedvalues() default {};
	
}
