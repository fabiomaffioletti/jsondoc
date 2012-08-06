package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.util.JSONDocMethod;

@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethod {

	public String path();

	public String description();
	
	public JSONDocMethod method();
	
	public String[] produces();
	
	public String[] consumes();
	
}
