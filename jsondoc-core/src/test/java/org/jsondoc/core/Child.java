package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="child")
public class Child extends Parent {
	
	@ApiObjectField(description="the test age")
	private Integer age;
	
	@ApiObjectField(description="the test games")
	private Long[] games;

}
