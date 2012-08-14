package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="location", show=false)
public class Location {

	@ApiObjectField(name="population", multiple=false, description="The population of the location", type="integer")
	private Integer population;
	@ApiObjectField(name="squarekm", multiple=false, description="The square km of the location", type="integer")
	private Integer squarekm;

	public Integer getPopulation() {
		return population;
	}

	public Integer getSquarekm() {
		return squarekm;
	}

}
