package org.jsondoc.core.util.pojo;

import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

@ApiObject(name = "child")
public class Child extends Parent {

	@ApiObjectField(description = "the test age")
	@ApiVersion(since = "1.0")
	private Integer age;

	@ApiObjectField(description = "the test games")
	private Long[] games;

	@ApiObjectField(description = "the scores for each game")
	private Map<String, Integer> scores;
	
	@ApiObjectField(name = "gender", description = "the gender of this person")
	@ApiVersion(since = "1.2")
	private Gender gender;

}
