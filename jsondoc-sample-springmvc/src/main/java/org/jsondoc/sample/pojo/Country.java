package org.jsondoc.sample.pojo;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="country", extendsclass="org.jsondoc.sample.pojo.Location")
public class Country extends Location {

	@ApiObjectField(description = "The name of the country", type = "string")
	private String name;

	@ApiObjectField(description = "The cities of the country", type = "city", multiple = true)
	private List<City> cities;
	
	public Country(String name, List<City> cities) {
		super();
		this.name = name;
		this.cities = cities;
	}

	public String getName() {
		return name;
	}

	public List<City> getCities() {
		return cities;
	}

}
