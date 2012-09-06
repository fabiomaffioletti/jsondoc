package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "city")
public class City extends Location {

	@ApiObjectField(description = "The name of the city")
	private String name;
	
	public City() {

	}

	public City(String name, Integer population, Integer squarekm) {
		super(population, squarekm);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
