package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="grandfather", show=false)
public class Grandfather {
	
	@ApiObjectField(type="string", multiple=false, description="the test surname")
	private String surname;
	
}
