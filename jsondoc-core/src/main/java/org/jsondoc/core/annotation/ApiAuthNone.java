package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your "service" class, for example controllers, or on exposed methods. It specifies that the
 * controller/method does not need any authentication, but on the contrary it is accessible by the anonymous user. 
 * @author Fabio Maffioletti
 *
 */

@Documented
@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuthNone {

}
