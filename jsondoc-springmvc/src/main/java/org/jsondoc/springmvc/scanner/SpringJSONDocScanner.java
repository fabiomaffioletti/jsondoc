package org.jsondoc.springmvc.scanner;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class SpringJSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class, true);
		Set<Class<?>> restControllers = reflections.getTypesAnnotatedWith(RestController.class, true);
		controllers.addAll(restControllers);
		return controllers;
	}
	
}
