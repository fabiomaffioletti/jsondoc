package org.jsondoc.springmvc.issues.invisible;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "another resource implementation")
public class AnotherResourceImplementation implements ResourceInterface {
	
	@ApiObjectField(name = "resource id")
	private String id;

	@Override
	public String getId() {
		return this.id;
	}

}
