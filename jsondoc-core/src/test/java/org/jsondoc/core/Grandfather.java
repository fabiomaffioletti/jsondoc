package org.jsondoc.core;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="grandfather", show=false)
public class Grandfather {
	
	@ApiObjectField(type="string", multiple=false, description="the test surname")
	private String surname;
	
	@ApiObjectField(type="date", multiple=false, description="the date of birth", format="yyyy-MM-dd HH:mm:ss")
	private Date dob;
	
}
