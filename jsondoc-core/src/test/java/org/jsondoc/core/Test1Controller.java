package org.jsondoc.core;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.http.MediaType;

@Api(name="Test1Controller", description="My test controller #1")
public class Test1Controller {
	
	@ApiMethod(
			path="/test1", 
			verb=ApiVerb.GET, 
			description="test method for controller 1", 
			consumes={MediaType.APPLICATION_JSON_VALUE},
			produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@ApiParams(urlparameters={
			@ApiParam(name="id", type="string", required=true, description="the test id param"),
			@ApiParam(name="count", type="integer", required=true, description="the test count param"),
	})
	@ApiHeaders(headers={
			@ApiHeader(name="application_id", description="The application's ID")
	})
	@ApiBodyObject(object="object", description="A test body object", multiple=false)
	@ApiResponseObject(object="response", description="A test response", multiple=true)
	@ApiErrors(apierrors={
			@ApiError(code="1000", description="A test error #1"),
			@ApiError(code="2000", description="A test error #2")
	})
	public void get(String id, Integer count, Object object) {
		
	}

}
