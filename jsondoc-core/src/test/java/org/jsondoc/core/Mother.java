package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="mother", extendsclass="org.jsondoc.core.Grandfather", show=true)
public class Mother extends Grandfather {

	@ApiObjectField(multiple=false, type="string", description="the test name")
	private String name;
	
}
