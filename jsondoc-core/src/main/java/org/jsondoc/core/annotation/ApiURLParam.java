package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value=ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiURLParam {

	public String name();

	public String description();
	
	public String type();
	
	public boolean required() default true;
	
	public String[] allowedvalues() default {};
	
}
