package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiErrors
 * @see ApiErrors
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiError {

	/**
	 * The error code returned
	 * @return
	 */
	public String code();

	/**
	 * A description of what the error code means
	 * @return
	 */
	public String description();
	
}
