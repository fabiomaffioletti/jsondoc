package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="location", show=false)
public class Location {

	@ApiObjectField(description="The population of the location")
	private Integer population;
	@ApiObjectField(description="The square km of the location")
	private Integer squarekm;

	public Integer getPopulation() {
		return population;
	}

	public Integer getSquarekm() {
		return squarekm;
	}

}
