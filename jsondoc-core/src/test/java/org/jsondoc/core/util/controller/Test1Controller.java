package org.jsondoc.core.util.controller;

import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.http.MediaType;

import java.util.List;

@Api(name="Test1Controller", description="My test controller #1")
public class Test1Controller {

	@ApiMethod(
			path="/test1",
			verb=ApiVerb.GET,
			description="test method for controller 1",
			consumes={MediaType.APPLICATION_JSON_VALUE},
			produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@ApiHeaders(headers={
			@ApiHeader(name="application_id", description="The application's ID")
	})
	@ApiErrors(apierrors={
			@ApiError(code="1000", description="A test error #1"),
			@ApiError(code="2000", description="A test error #2")
	})
    @ApiParams(parameters = {
            @ApiParam(name = "token", description = "a token", required = false, paramType = ApiParamType.QUERY)})
	public @ApiResponseObject List<Integer> get(@ApiParam(name="id", description="abc", paramType=ApiParamType.PATH) String id, @ApiParam(name="count", description="xyz", paramType=ApiParamType.PATH) Integer count, @ApiBodyObject String name) {
		return null;
	}

}
