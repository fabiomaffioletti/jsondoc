package org.jsondoc.core.util.pojo;

import org.jsondoc.core.annotation.ApiObjectField;

public class StackOverflowTemplateObjectOne {

	@ApiObjectField(description = "The user id")
	private Long id;

	@ApiObjectField(description = "The user firstname")
	private String firstname;

	@ApiObjectField(description = "The user's default group", processtemplate = false)
	private StackOverflowTemplateObjectTwo defaultgroup;

}
