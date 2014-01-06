package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(name = "user services", description = "Methods for managing users")
@Controller
@RequestMapping(value = "/users")
public class UserController {

	@ApiMethod(path = "/users/{id}", verb = ApiVerb.GET, description = "Gets a user with the given ID", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	User user(@PathVariable @ApiParam(name = "id", description = "The user's ID", required = true, paramType=ApiParamType.PATH) Integer id) {
		return new User(id, "jsondoc-user", 30, "M");
	}

	@ApiMethod(path = "/users/{gender}/{age}", verb = ApiVerb.GET, description = "Gets users with the given gender and age", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/{gender}/{age}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<User> userAge(@PathVariable @ApiParam(name = "gender", description = "The user's gender", required = true, paramType=ApiParamType.PATH) String gender, @PathVariable @ApiParam(name = "age", description = "The user's required age", required = true, paramType=ApiParamType.PATH) Integer age) {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "jsondoc-user-1", age, gender));
		users.add(new User(2, "jsondoc-user-2", age, gender));
		return users;
	}
	
	@ApiMethod(path = "/users?name={name}", verb = ApiVerb.GET, description = "Gets a user with the given name", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	User userByName(@RequestParam("name") @ApiParam(name = "name", description = "The user's name", required = true, paramType=ApiParamType.QUERY) String name) {
		return new User(1, name, 30, "M");
	}
	
	@ApiMethod(path = "/users/q/{name}/{gender}?agemin={agemin}&agemax={agemax}", verb = ApiVerb.GET, description = "Gets a user with the given gender and given age", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/q/{name}/{gender}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<User> usersByNameAndGenderAndAge(@PathVariable("name") @ApiParam(name = "name", description = "The user's name", required = true, paramType=ApiParamType.PATH) String name, @PathVariable("gender") @ApiParam(name = "gender", description = "The user's gender", required = true, paramType=ApiParamType.PATH) String gender, @RequestParam(value="agemin") @ApiParam(name = "agemin", description = "The user's min age", required = false, paramType=ApiParamType.QUERY) Integer agemin, @RequestParam(value="agemax") @ApiParam(name = "agemax", description = "The user's max age", required = false, paramType=ApiParamType.QUERY) Integer agemax) {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "jsondoc-user-1", agemin, gender));
		users.add(new User(2, "jsondoc-user-2", agemax, gender));
		return users;
	}
	
	@ApiMethod(path = "/users/map", verb = ApiVerb.POST, description = "Post test for map request body", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	public @ResponseBody @ApiResponseObject
	List<User> usersWithMapBodyObject(@ApiBodyObject @RequestBody Map<String, Integer> map) {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "jsondoc-user-1", 1, "M"));
		return users;
	}
	
	@ApiMethod(path = "/users/wildcardParametrizedList", verb = ApiVerb.GET, description = "Gets a list of users. This is a test for wildcard parametrized list", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/wildcardParametrizedList", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<?> wildcardParametrizedList(@ApiParam(name="wildcardParametrizedList", paramType=ApiParamType.QUERY) String wildcardParametrizedList) {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "jsondoc-user-1", 1, "M"));
		return users;
	}

}
