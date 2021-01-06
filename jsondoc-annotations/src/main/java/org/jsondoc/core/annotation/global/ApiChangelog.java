package org.jsondoc.core.annotation.global;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiChangelogSet
 * 
 * @see ApiChangelogSet
 * @author Fabio Maffioletti
 * 
 */
@Documented
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiChangelog {

	/**
	 * Api version this changelog refers to
	 * 
	 * @return
	 */
	public String version();

	/**
	 * List of changes introduced in this api version
	 * @return
	 */
	public String[] changes();

}
