package org.jsondoc.core;

import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="child")
public class Child extends Parent {
	
	@ApiObjectField(description="the test age")
	private Integer age;
	
	@ApiObjectField(description="the test games")
	private Long[] games;
	
	@ApiObjectField(description="the scores for each game")
	private Map<String, Integer> scores;

}
