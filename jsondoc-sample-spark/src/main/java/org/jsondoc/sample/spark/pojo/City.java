package org.jsondoc.sample.spark.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "city")
public class City {
	
	@ApiObjectField(name = "name", description = "The city name")
	private String name;
	
	public City(String name) {
		this.name = name;
	}

}
