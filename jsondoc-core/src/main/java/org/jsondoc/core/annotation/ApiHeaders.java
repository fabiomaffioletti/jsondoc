package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and contains an array of
 * ApiHeader
 * 
 * @see ApiHeader
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeaders {

	/**
	 * An array of ApiHeader annotations
	 * 
	 * @see ApiHeader
	 * @return
	 */
	public ApiHeader[] headers();

}
