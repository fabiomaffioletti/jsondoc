package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="son")
public class Son extends Father {
	
	@ApiObjectField(type="integer", multiple=false, description="the test age")
	private Integer age;

}
