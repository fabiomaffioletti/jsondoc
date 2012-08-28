package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and represents the parameter passed in the body of the requests
 * @see ApiObject
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiBodyObject {
	
}
