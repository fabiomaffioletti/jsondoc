package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.data.SampleData;
import org.jsondoc.sample.pojo.City;
import org.jsondoc.sample.pojo.Continent;
import org.jsondoc.sample.pojo.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(name="country services", description="Methods for managing countries", group = "Geography")
@Controller
@ApiVersion(since = "1.0", until = "2.12")
@RequestMapping(value="/countries")
public class CountryController {
	
	@ApiMethod(
		path="/countries/{name}", 
		verb=ApiVerb.GET, 
		description="Gets a country with the given name.",
		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	@ApiErrors(apierrors={
		@ApiError(code="1000", description="Country not found"),
		@ApiError(code="9000", description="Illegal argument")
	})
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public @ResponseBody @ApiResponseObject Country getCountryByName(@PathVariable @ApiParam(name="name", paramType=ApiParamType.PATH) String name) {
		List<City> cities = new ArrayList<City>();
		cities.add(new City("Sydney", 19329, 43));
		cities.add(new City("Melbourne", 85743, 12));
		cities.add(new City("Perth", 58735, 39));
		return new Country(32198, 5487, "Australia", cities, Continent.AUSTRALIA);
	}
	
	@ApiMethod(
		path="/countries", 
		verb=ApiVerb.GET, 
		description="Gets all the countries",
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody @ApiResponseObject List<Country> getAllCountries() {
		return SampleData.countries;
	}
	
	@ApiMethod(
		path="/countries", 
		verb=ApiVerb.POST, 
		description="Saves a country, with a list of cities",
		produces={MediaType.APPLICATION_JSON_VALUE},
		consumes={MediaType.APPLICATION_JSON_VALUE}
	)
	@ApiHeaders(headers={
		@ApiHeader(name="application_id", description="The application id")
	})
	@ApiErrors(apierrors={
		@ApiError(code="5000", description="Duplicate country"),
		@ApiError(code="6000", description="Validation error"),
		@ApiError(code="7000", description="Invalid application id"),
		@ApiError(code="9000", description="Illegal argument")
	})
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody @ApiResponseObject Country saveCountry(@RequestBody @ApiBodyObject Country country) {
		return country;
	}

	@ApiMethod(
		path="/countries/{id}", 
		verb=ApiVerb.DELETE, 
		description="Deletes the country with the given id",
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@ApiHeaders(headers={
		@ApiHeader(name="application_id", description="The application id")
	})
	@ApiErrors(apierrors={
		@ApiError(code="1000", description="Country not found"),
		@ApiError(code="7000", description="Invalid application id"),
		@ApiError(code="9000", description="Illegal argument")
	})
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void deleteCountry(@PathVariable @ApiParam(name="id", paramType=ApiParamType.PATH) Integer id) {
		
	}
}
