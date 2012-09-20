package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.City;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	@ApiErrors(apierrors={
			@ApiError(code="2000", description="City not found"),
			@ApiError(code="9000", description="Illegal argument")
	})
	@RequestMapping(value="/get/{name}", method=RequestMethod.GET)
	public @ResponseBody @ApiResponseObject City getCityByName(@PathVariable @ApiParam(name="name", description="The city name", allowedvalues={"Melbourne", "Sydney", "Perth"}) String name) {
		return new City(name, 1982700, 52);
	}
	
	@ApiMethod(
			path="/city/save", 
			verb=ApiVerb.POST, 
			description="Saves a city",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
		)
		@ApiHeaders(headers={
				@ApiHeader(name="api_id", description="The api identifier")
		})
		@ApiErrors(apierrors={
				@ApiError(code="3000", description="City already existing"),
				@ApiError(code="9000", description="Illegal argument")
		})
		@RequestMapping(value="/save", method=RequestMethod.POST)
		public @ResponseBody @ApiResponseObject City save(@RequestBody @ApiBodyObject City city) {
			return city;
		}

}
