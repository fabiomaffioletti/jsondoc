package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;

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

	/**
	 * With this it is possible to specify the logical grouping of this API. For example, if you have APIs like city services or country services, you can group them
	 * in the "Geography" group, while if you have author services and book services you can group them in the "Library" group.
	 * @return
	 */
	public String group() default "";
	
	/**
	 * Indicates the visibility of the api
	 * @return
	 */
	public ApiVisibility visibility() default ApiVisibility.UNDEFINED;

	/**
	 * Indicates the stage of development or release
	 * @return
	 */
	public ApiStage stage() default ApiStage.UNDEFINED;
}
