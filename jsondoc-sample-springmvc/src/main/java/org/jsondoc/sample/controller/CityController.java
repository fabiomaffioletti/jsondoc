package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiURLParam;
import org.jsondoc.core.util.JSONDocMethod;
import org.jsondoc.sample.pojo.City;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(path="/city", description="Methods for managing cities")
@Controller
@RequestMapping(value="/city")
public class CityController {
	
	@ApiMethod(
			path="/get/{name}", 
			method=JSONDocMethod.POST, 
			description="Gets Sydney",
			produces={MediaType.APPLICATION_JSON_VALUE},
			consumes={MediaType.APPLICATION_JSON_VALUE}
			)
	@ApiErrors(apierrors={
			@ApiError(code="1000", description="City not found"),
			@ApiError(code="2000", description="City unexisting"),
			@ApiError(code="9000", description="Invalid parameter")
	})
	@RequestMapping(value="/get/{name}", method=RequestMethod.GET)
	public 
	@ApiResponseObject(
			object="city", 
			description="A city object", 
			multiple = true) 
	@ResponseBody City getCityByName(
			@ApiURLParam(
					name="name", 
					required = false, 
					description="name description",
					type = "string",
					allowedvalues = {"sydney", "melbourne", "perth"}
					) 
			@PathVariable String name) {
		return new City("sydney");
	}

}
