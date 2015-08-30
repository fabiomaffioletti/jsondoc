package org.jsondoc.core.annotation.global;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiVerb;

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
public @interface ApiGlobalVerb {

	/**
	 * The verb (GET, POST, ...) that is documented
	 * @return
	 */
	public ApiVerb verb();
	
	/**
	 * When and for what reason this verb is used in your api 
	 * @return
	 */
	public String description();
	
}
