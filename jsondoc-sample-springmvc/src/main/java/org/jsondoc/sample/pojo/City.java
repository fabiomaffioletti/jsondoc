package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiPojo;
import org.jsondoc.core.annotation.ApiPojoField;

@ApiPojo(name = "city")
public class City {

	@ApiPojoField(name = "name", description = "city name", type = "string", multiple = false)
	private String name;

	public String getName() {
		return name;
	}

	public City(String name) {
		super();
		this.name = name;
	}

}
