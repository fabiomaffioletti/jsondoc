package org.jsondoc.springmvc.scanner;

import java.util.Set;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class Spring4JSONDocScanner extends AbstractSpringJSONDocScanner {
	
	@Override
	public Set<Class<?>> jsondocControllers() {
		Set<Class<?>> jsondocControllers = reflections.getTypesAnnotatedWith(Controller.class, true);
		jsondocControllers.addAll(reflections.getTypesAnnotatedWith(RestController.class, true));
		
		try {
			Class.forName("org.springframework.data.rest.webmvc.RepositoryRestController");
			jsondocControllers.addAll(reflections.getTypesAnnotatedWith(RepositoryRestController.class, true));
			
		} catch (ClassNotFoundException e) {
			log.debug(e.getMessage() + ".class not found");
		}
		
		return jsondocControllers;
	}
	
}
