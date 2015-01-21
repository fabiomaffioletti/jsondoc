package org.jsondoc.core.util.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.util.pojo.Pizza;

@Api(description = "Annotations put on an interface instead of on a concrete class", name = "interface services")
public interface PizzaController {
	
	@ApiMethod(path = "/pizzas/pizza/{id}", verb = ApiVerb.GET, produces = "application/json")
	@ApiResponseObject Pizza get(@ApiPathParam(name = "id") Long id);

}
