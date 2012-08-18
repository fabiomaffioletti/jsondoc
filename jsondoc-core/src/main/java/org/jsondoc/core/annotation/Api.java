package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your "service" class, for example controller classes in Spring MVC.
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

	/**
	 * A description of what the API does
	 * @return
	 */
	public String description();
	
	/**
	 * The name of the API
	 * @return
	 */
	public String name();

}
