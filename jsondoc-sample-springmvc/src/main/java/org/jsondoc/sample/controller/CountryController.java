package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiRequestBodyObject;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiURLParam;
import org.jsondoc.core.util.JSONDocMethod;
import org.jsondoc.sample.pojo.City;
import org.jsondoc.sample.pojo.Country;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(path="/country", description="Methods for managing countries")
@Controller
@RequestMapping(value="/country")
public class CountryController {
	
	@ApiMethod(
			path="/get/{name}", 
			method=JSONDocMethod.GET, 
			description="Gets Australia",
			produces={MediaType.APPLICATION_JSON_VALUE},
			consumes={MediaType.APPLICATION_JSON_VALUE}
			)
	@RequestMapping(value="/get/{name}", method=RequestMethod.GET)
	public 
	@ApiResponseObject(
			object="country", 
			description="A Country object", 
			multiple = false) 
	@ResponseBody Country getCountryByName(
			@ApiURLParam(
					name="name", 
					description="country description",
					type = "string"
					) @PathVariable String name) {
		List<City> cities = new ArrayList<City>();
		cities.add(new City("Melbourne"));
		cities.add(new City("Adelaide"));
		return new Country("australia", 1892, cities);
	}
	
	@ApiMethod(
			path="/save", 
			method=JSONDocMethod.GET, 
			description="Saves a country",
			produces={MediaType.APPLICATION_JSON_VALUE},
			consumes={MediaType.APPLICATION_JSON_VALUE}
			)
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public 
	@ApiResponseObject(
			object="string", 
			description="A String representing the result of the operation", 
			multiple = false) 
	@ResponseBody String saveCountry(
			@ApiRequestBodyObject(object="country", description="A country object", multiple=false) Country country) {
		
		return "country saved";
	}

}
