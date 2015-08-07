package org.jsondoc.springmvc.scanner;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class SpringJSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true);
		jsondocControllers.addAll(reflections.getTypesAnnotatedWith(RestController.class, true));
		return jsondocControllers;
	}
	
}
