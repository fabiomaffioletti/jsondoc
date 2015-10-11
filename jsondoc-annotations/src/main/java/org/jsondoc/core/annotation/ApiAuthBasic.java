package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your "service" class, for example controllers, or on exposed methods. It specifies that the
 * controller/method needs basic authentication. 
 * @author Fabio Maffioletti
 *
 */

@Documented
@Target(value = { ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuthBasic {
	
	/**
	 * The role(s) a user must have to correctly access the annotated controller/method. Defaults to *
	 * @return
	 */
	public String[] roles() default { "*" };
	
	/**
	 * A list of test users that can be used to test methods with different username/password/roles combinations
	 * @return
	 */
	public ApiAuthBasicUser[] testusers() default {};

}
