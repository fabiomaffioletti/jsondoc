package org.jsondoc.springmvc.scanner;

import java.util.Set;

import org.springframework.stereotype.Controller;

public class Spring3JSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true); 
		return jsondocControllers;
	}
	
}
