package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;

@ApiObject(name = "continent", description = "An enum of continents", group = "Geography")
public enum Continent {
	
	AFRICA, AMERICA, ANTARCTICA, ASIA, AUSTRALIA, EUROPE;  

}
