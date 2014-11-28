package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.jsondoc.sample.pojo.City;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(name = "city services", description = "Methods for managing cities", group = "Geography")
@Controller
@ApiVersion(since = "1.0")
@RequestMapping(value = "/cities")
public class CityController {

	@ApiMethod(path = "/cities/name/{name}", verb = ApiVerb.GET, description = "Gets a city with the given name. (Allowed values are just to demonstrate the annotation attribute)", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiVersion(since = "1.0", until = "2.12")
	@ApiErrors(apierrors = { @ApiError(code = "2000", description = "City not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public @ResponseBody
	@ApiResponseObject
	City get(@PathVariable @ApiParam(name = "name", description = "The city name", allowedvalues = { "Melbourne", "Sydney", "Perth" }, paramType=ApiParamType.PATH) String name) {
		return new City(name, 1982700, 52);
	}

	@ApiMethod(path = "/cities", verb = ApiVerb.POST, description = "Saves a city", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiVersion(since = "1.2-SNAPSHOT")
	@ApiHeaders(headers = { @ApiHeader(name = "api_id", description = "The api identifier") })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "City already existing"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	@ApiResponseObject
	City post(@RequestBody @ApiBodyObject City city) {
		return city;
	}

	@ApiMethod(path = "/cities/{id}", verb = ApiVerb.DELETE, description = "Deleted a city by its ID")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	void delete(@PathVariable @ApiParam(name = "id", description = "The city ID", paramType=ApiParamType.PATH) Integer id) {

	}
	
	@ApiMethod(path = "/cities/{id}", verb = ApiVerb.GET, description = "Gets a city by its ID", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	@ApiResponseObject
	City getById(@PathVariable @ApiParam(name = "id", description = "The city ID", paramType=ApiParamType.PATH) Integer id) {
		return new City("cityById", 1982700, 52);
	}

	@ApiMethod(path = "/cities/{id}", verb = ApiVerb.PUT, description = "Modifies a city", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	@ApiResponseObject
	City put(@PathVariable @ApiParam(name = "id", description = "The city ID", paramType=ApiParamType.PATH) Integer id, @RequestBody @ApiBodyObject City city) {
		return city;
	}

	@ApiMethod(path = "/cities/map", verb = ApiVerb.GET, description = "Gets a map of cities", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/map", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ApiResponseObject
	@ResponseBody
	Map<String, City> map() {
		Map<String, City> cities = new HashMap<String, City>();
		cities.put("a", new City("Adelaide", 4322, 8));
		cities.put("m", new City("Melbourne", 9080, 12));
		cities.put("p", new City("Perth", 743534, 5));
		cities.put("s", new City("Sydney", 54654, 32));
		return cities;
	}
	
	@ApiMethod(path = "/cities/map/list", verb = ApiVerb.GET, description = "Gets a map of list of cities", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/map/list", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ApiResponseObject
	@ResponseBody
	Map<List<String>, City> mapList() {
		Map<List<String>, City> cities = new HashMap<List<String>, City>();
		List<String> a = new ArrayList<String>();
		a.add("a1");
		a.add("a2");
		List<String> m = new ArrayList<String>();
		m.add("m1");
		List<String> p = new ArrayList<String>();
		p.add("p1");
		List<String> s = new ArrayList<String>();
		s.add("s1");
		s.add("s2");
		s.add("s3");
		cities.put(a, new City("Adelaide", 4322, 8));
		cities.put(m, new City("Melbourne", 9080, 12));
		cities.put(p, new City("Perth", 743534, 5));
		cities.put(s, new City("Sydney", 54654, 32));
		return cities;
	}

}
