package org.jsondoc.core;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="grandparent", show=false)
public class Grandparent {
	
	@ApiObjectField(description="the test surname")
	private String surname;
	
	@ApiObjectField(description="the date of birth", format="yyyy-MM-dd HH:mm:ss")
	private Date dob;
	
}
