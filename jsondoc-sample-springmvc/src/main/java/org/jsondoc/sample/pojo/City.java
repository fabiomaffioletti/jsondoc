package org.jsondoc.sample.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

@ApiObject(name = "city", group = "Geography")
@ApiVersion(since = "1.3", until = "1.4")
@XmlRootElement
public class City extends Location {

	@ApiObjectField(description = "The name of the city", allowedvalues = { "Melbourne", "Sydney", "Perth" })
	@ApiVersion(since = "1.36", until = "1.4")
	@XmlElement
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
