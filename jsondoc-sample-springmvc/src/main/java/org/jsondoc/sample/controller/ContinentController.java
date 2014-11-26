package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.Continent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(name = "continent services", description = "Methods for managing continents", group = "Geography")
@Controller
@RequestMapping(value = "/continents")
public class ContinentController {

	@ApiMethod(path = "/continents/africa", verb = ApiVerb.GET, description = "Gets Africa.", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/africa", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject Continent getAfrica() {
		return Continent.AFRICA;
	}

	@ApiMethod(path = "/continents/america", verb = ApiVerb.GET, description = "Gets America.", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/america", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject Continent getAmerica() {
		return Continent.AMERICA;
	}
	
	@ApiMethod(path = "/continents/australia", verb = ApiVerb.GET, description = "Gets Australia.", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/australia", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject Continent getAustralia() {
		return Continent.AUSTRALIA;
	}

	@ApiMethod(path = "/continents/{continent}", verb = ApiVerb.GET, description = "Gets a continent by name.", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/{continent}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject Continent getContinentByName(@ApiParam(name = "continent", paramType = ApiParamType.PATH) @PathVariable("continent") Continent continent) {
		return Continent.valueOf(continent.name());
	}

}
