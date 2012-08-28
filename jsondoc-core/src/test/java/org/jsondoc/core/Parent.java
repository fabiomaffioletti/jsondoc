package org.jsondoc.core;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="parent")
public class Parent extends Grandparent {
	
	@ApiObjectField(description="the test name")
	private String name;

	@ApiObjectField(description="the test name")
	private List<Child> children;
}
