package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Api(name = "type services", description = "Methods for testing correct response/body/param types")
@Controller
@RequestMapping(value = "/type")
public class TypeController {

	@ApiMethod(path = "/type/string", verb = ApiVerb.GET, description = "Gets a string", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/string", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	String string() {
		return "ok";
	}
	
	@ApiMethod(path = "/type/integer", verb = ApiVerb.GET, description = "Gets an integer", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/integer", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	Integer integer() {
		return 1;
	}
	
	@ApiMethod(path = "/type/list/string", verb = ApiVerb.GET, description = "Gets a list of string", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/list/string", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<String> listOfString() {
		return new ArrayList<String>();
	}
	
	@ApiMethod(path = "/type/list/set/long", verb = ApiVerb.GET, description = "Gets a list of set of long", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/list/set/long", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<Set<Long>> listOfSetOfLong() {
		return Lists.newArrayList();
	}
	
	@ApiMethod(path = "/type/array/user", verb = ApiVerb.GET, description = "Gets an array of user", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/array/user", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	User[] arrayOfUser() {
		return new User[]{};
	}
	
	@ApiMethod(path = "/type/array/byte", verb = ApiVerb.GET, description = "Gets an array of byte", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/array/byte", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	byte[] arrayOfByte() {
		return new byte[]{};
	}
	
	@ApiMethod(path = "/type/map/string/integer", verb = ApiVerb.GET, description = "Gets a map where key is string and value is integer", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/map/string/integer", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	Map<String, Integer> mapStringInteger() {
		return Maps.newHashMap();
	}
	
	@ApiMethod(path = "/type/map/list/string/integer", verb = ApiVerb.GET, description = "Gets a map where key is a list of string and value is an integer", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/map/list/string/integer", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	Map<List<String>, Integer> mapListOfStringInteger() {
		return Maps.newHashMap();
	}

}
