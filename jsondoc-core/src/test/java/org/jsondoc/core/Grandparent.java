package org.jsondoc.core;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="grandfather", show=false)
public class Grandparent {
	
	@ApiObjectField(type="string", description="the test surname")
	private String surname;
	
	@ApiObjectField(type="date", description="the date of birth", format="yyyy-MM-dd HH:mm:ss")
	private Date dob;
	
}
