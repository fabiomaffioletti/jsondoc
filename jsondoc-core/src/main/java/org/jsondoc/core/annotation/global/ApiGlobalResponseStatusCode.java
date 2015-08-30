package org.jsondoc.core.annotation.global;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiGlobal
 * 
 * @see ApiGlobal
 * @author Fabio Maffioletti
 * 
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiGlobalResponseStatusCode {

	/**
	 * The response status code (200 - OK, 201 - CREATED, ...)
	 * @return
	 */
	public String code();
	
	/**
	 * When and for what reason this code is returned by your api 
	 * @return
	 */
	public String description();
	
}
