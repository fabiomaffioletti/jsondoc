package org.jsondoc.core.annotation.flow;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotaion of type @ApiFlow and references a method previously documented with the JSONDoc @ApiMethod
 * annotation in which the "id" property is specified
 * 
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFlowStep {
	
	/**
	 * The api method identified by this id is the method used in the flow step
	 * @return
	 */
	public String apimethodid();

}
