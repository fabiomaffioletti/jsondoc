package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.City;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(name="city services", description="Methods for managing cities")
@Controller
@RequestMapping(value="/city")
public class CityController {
	
	@ApiMethod(
		path="/city/get/{name}", 
		verb=ApiVerb.GET, 
		description="Gets a city with the given name, provided that the name is between sydney, melbourne and perth",
		produces={MediaType.APPLICATION_JSON_VALUE},
		consumes={MediaType.APPLICATION_JSON_VALUE}
	)
	@ApiErrors(apierrors={
			@ApiError(code="2000", description="City not found"),
			@ApiError(code="9000", description="Illegal argument")
	})
	@RequestMapping(value="/get/{name}", method=RequestMethod.GET)
	public @ResponseBody @ApiResponseObject City getCityByName(@PathVariable @ApiParam(name="name", description="The city name", allowedvalues={"Melbourne", "Sydney", "Perth"}) String name, @PathVariable @ApiParam(name="path", description="The city path") String path) {
		// Here goes the method implementation
		return null;
	}

}
