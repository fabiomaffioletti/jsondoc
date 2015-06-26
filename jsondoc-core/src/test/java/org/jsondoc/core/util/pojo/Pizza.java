package org.jsondoc.core.util.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "customPizzaObject", group = "Restaurant")
public class Pizza extends Parent {

	@ApiObjectField(description = "the cost of this pizza")
	private Float price;

	@ApiObjectField(description = "the topping of this pizza")
	private String[] topping;

}
