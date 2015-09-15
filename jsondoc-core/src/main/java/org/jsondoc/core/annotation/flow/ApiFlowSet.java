package org.jsondoc.core.annotation.flow;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on a type (typically on a class dedicated to
 * flow description) and will trigger scanning for @ApiFlow annotations
 * 
 * @see ApiFlowStep
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFlowSet {
	
}
