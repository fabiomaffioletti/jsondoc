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
@Target(value = { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

	/**
	 * The version since the method/object/class is supported
	 * @return
	 */
	public String since();
	
	/**
	 * The version until the method/object/class is supported
	 * @return
	 */
	public String until() default "";

}
