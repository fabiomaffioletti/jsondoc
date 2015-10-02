package org.jsondoc.core.issues.issue151;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(group = "bargroup", description = "Bar description")
public class BarPojo {

	@ApiObjectField(description = "Bar description")
	private String barField;

}
