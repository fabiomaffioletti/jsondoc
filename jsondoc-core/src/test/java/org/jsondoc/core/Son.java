package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="son", extendsclass="org.jsondoc.core.Father", show=true)
public class Son {
	
	@ApiObjectField(type="integer", multiple=false, description="the test age")
	private Integer age;

}
