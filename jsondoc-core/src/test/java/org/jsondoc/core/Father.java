package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="father", extendsclass="org.jsondoc.core.Grandfather", show=true)
public class Father {
	
	@ApiObjectField(name="name", multiple=false, type="string", description="the test name")
	private String name;

}
