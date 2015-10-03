package org.jsondoc.core.issues.issue151;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;

@Api(name = "Foo Services", description = "bla, bla, bla ...", group = "foogroup")
public class FooController {

	@ApiMethod(path = { "/api/foo" }, description = "Main foo service")
	@ApiResponseObject
	public FooWrapper<BarPojo> getBar() {
		return null;
	}
	
	@ApiMethod(path = { "/api/foo-wildcard" }, description = "Main foo service with wildcard")
	@ApiResponseObject
	public FooWrapper<?> wildcard() {
		return null;
	}

}