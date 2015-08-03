package org.jsondoc.core.util.controller;

import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiVerb;

import java.util.List;

@Api(name="Test3Controller", description="My test controller #3")
@ApiVersion(since = "1.0")
@ApiErrors(apierrors = {@ApiError(code = "400", description = "Common bad exception"),
		@ApiError(code = "1000", description = "This exception will be overridden by the one defined at method level")})
@ApiAuthNone
public class Test3Controller {
	
	@ApiMethod(
			path="/test3",
			verb=ApiVerb.GET, 
			description="test method for controller 3",
			consumes={"application/json"},
			produces={"application/json"}
	)
	@ApiVersion(since = "1.0")
	@ApiHeaders(headers={
			@ApiHeader(name="application_id", description="The application's ID")
	})
	@ApiErrors(apierrors={
			@ApiError(code="1000", description="A test error #1"),
			@ApiError(code="2000", description="A test error #2")
	})
	public @ApiResponseObject List<Integer> get(
			@ApiPathParam(name="id", description="abc") String id, 
			@ApiPathParam(name="count", description="xyz") Integer count, 
			@ApiBodyObject String name) {
		return null;
	}

}
