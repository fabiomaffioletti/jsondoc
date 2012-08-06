package org.jsondoc.sample.pojo;

import java.util.List;

import org.jsondoc.core.annotation.ApiPojo;
import org.jsondoc.core.annotation.ApiPojoField;

@ApiPojo(name="country")
public class Country {

	@ApiPojoField(name = "name", description = "country name", type = "string", multiple = false)
	private String name;

	@ApiPojoField(name = "population", description = "country population", type = "integer", multiple = false)
	private Integer population;

	@ApiPojoField(name = "cities", description = "country cities", type = "city", multiple = true)
	private List<City> cities;
	
	public Country(String name, Integer population, List<City> cities) {
		super();
		this.name = name;
		this.population = population;
		this.cities = cities;
	}

	public String getName() {
		return name;
	}

	public Integer getPopulation() {
		return population;
	}

	public List<City> getCities() {
		return cities;
	}

}
