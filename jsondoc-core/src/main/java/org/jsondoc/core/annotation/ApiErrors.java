package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * This annotation is to be used on your controller class or method and represents the possible errors
 * returned by the API endpoints.
 *
 * <p>
 *     When the @ApiErrors is present at the class level, defined errors will be applied to all member methods.
 *     Member methods can override the errors declared at the class level and provide more specific ones.
 *
 *
 * @see ApiError
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrors {

	/**
	 * An array of ApiError annotations
	 * @see ApiError
	 * @return
	 */
	public ApiError[] apierrors();
	
}
