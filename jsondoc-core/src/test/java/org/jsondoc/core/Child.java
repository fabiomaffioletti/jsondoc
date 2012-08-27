package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="child")
public class Child extends Father {
	
	@ApiObjectField(type="integer", multiple=false, description="the test age")
	private Integer age;

}
