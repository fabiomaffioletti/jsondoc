package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiAuthBasic. It lets you specify username and password for users that
 * can/cannot access the annotated controller/method.
 * @author fmaffioletti
 *
 */

@Documented
@Target(value = { ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuthBasicUser {
	
	/**
	 * The test user's username
	 * @return
	 */
	public String username();
	
	/**
	 * The test user's password
	 * @return
	 */
	public String password();

}
