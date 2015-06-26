package org.jsondoc.core.util.pojo;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

@ApiObject(name = "parent")
public class Parent extends Grandparent {

	@ApiObjectField(description = "the test name")
	private String name;

	@ApiObjectField(description = "the test name")
	@ApiVersion(since = "1.0", until = "2.12")
	private List<Child> children;
}
