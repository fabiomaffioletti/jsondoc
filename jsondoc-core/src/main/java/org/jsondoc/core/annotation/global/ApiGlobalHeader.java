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
public @interface ApiGlobalHeader {

	/**
	 * The header name
	 * @return
	 */
	public String headername();
	
	/**
	 * The header value
	 * @return
	 */
	public String headervalue() default "";
	
	/**
	 * The description of this header 
	 * @return
	 */
	public String description();
	
}
