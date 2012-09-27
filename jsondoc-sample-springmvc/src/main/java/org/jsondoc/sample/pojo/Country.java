package org.jsondoc.sample.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "country")
@XmlRootElement
public class Country extends Location {

	@ApiObjectField(description = "The name of the country")
	@XmlElement
	private String name;

	@ApiObjectField(description = "The cities of the country")
	@XmlElement
	private List<City> cities;

	public Country() {

	}

	public Country(Integer population, Integer squarekm, String name, List<City> cities) {
		super(population, squarekm);
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
