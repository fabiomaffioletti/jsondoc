package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthBasic;
import org.jsondoc.core.annotation.ApiAuthBasicUser;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(name = "authenticated services", description = "Authenticated methods")
@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@ApiMethod(path = "/auth/basicauth", verb = ApiVerb.GET, description = "A basic authenticated method", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiAuthBasic(roles = {"ROLE_USER", "ROLE_ADMIN"}, testusers = {@ApiAuthBasicUser(username = "user", password = "123456"), @ApiAuthBasicUser(username = "admin", password = "123456")})
	@ApiErrors(apierrors = { @ApiError(code = "8000", description = "Invalid credentials") })
	@RequestMapping(value = "/basicauth", method = RequestMethod.GET)
	public @ResponseBody
	@ApiResponseObject
	String basicauth() {
		return "basicauth";
	}
	
	@ApiMethod(path = "/auth/basicauthnouser", verb = ApiVerb.GET, description = "A basic authenticated method with no test users", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiAuthBasic(roles = {"ROLE_USER", "ROLE_ADMIN"})
	@ApiErrors(apierrors = { @ApiError(code = "8000", description = "Invalid credentials") })
	@RequestMapping(value = "/basicauthnouser", method = RequestMethod.GET)
	public @ResponseBody
	@ApiResponseObject
	String basicauthnouser() {
		return "basicauthnouser";
	}
	
	@ApiMethod(path = "/auth/noauth", verb = ApiVerb.GET, description = "A method available to everyone ", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiAuthNone
	@RequestMapping(value = "/noauth", method = RequestMethod.GET)
	public @ResponseBody
	@ApiResponseObject
	String noauth() {
		return "noauth";
	}
	
	@ApiMethod(path = "/auth/undefinedauth", verb = ApiVerb.GET, description = "A method with no annotation regarding auth", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequestMapping(value = "/undefinedauth", method = RequestMethod.GET)
	public @ResponseBody
	@ApiResponseObject
	String undefinedauth() {
		return "undefinedauth";
	}
	
}
