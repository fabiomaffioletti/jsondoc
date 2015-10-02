package org.jsondoc.springmvc.issues.issue151;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(group = "foogroup", description = "Foo description")
public class FooWrapper<T> {

	@ApiObjectField(description = "The wrapper's content")
	private T content;

}
