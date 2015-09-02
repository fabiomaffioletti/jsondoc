package org.jsondoc.core.annotation.global;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation has to be used in a ApiGlobal annotation. It represents a block of information of the same context
 * like http verbs, authentication, common headers, etc 
 * 
 * @see ApiGlobal
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiGlobalSection {
	
	/**
	 * The title of this section
	 * @return
	 */
	public String title();
	
	/**
	 * An array that can be made of two things: a text (may contain html tags) or a path to a file in the classpath, 
	 * containing  a text. In this case the array item should have the /jsondocfile prefix, for example: 
	 * /jsondocfile./src/main/resources/jsondoc/global-verbs.html
	 * @return
	 */
	public String[] paragraphs() default {};

}
