package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="child", extendsclass="org.jsondoc.core.Father", show=true)
public class Child {
	
	@ApiObjectField(type="integer", multiple=false, description="the test age")
	private Integer age;

}
