package org.jsondoc.springmvc.annotation;

import java.lang.annotation.*;

@Documented
@Target(value= ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringApiParam {
   	/**
   	 * A description of what the method does
   	 * @return
   	 */
   	public String description();
}
