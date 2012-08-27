package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "city", extendsclass="org.jsondoc.sample.pojo.Location")
public class City extends Location {

	@ApiObjectField(description = "The name of the city", type = "string")
	private String name;

	public City(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
