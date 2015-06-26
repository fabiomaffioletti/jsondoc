package org.jsondoc.core.util.pojo;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

@ApiObject(name = "grandparent", show = false)
public class Grandparent {

	@ApiObjectField(description = "the test surname")
	@ApiVersion(since = "1.0")
	private String surname;

	@ApiObjectField(description = "the date of birth", format = "yyyy-MM-dd HH:mm:ss")
	private Date dob;

}
