package org.jsondoc.core.util.pojo;

import org.jsondoc.core.annotation.ApiObjectField;


public class NotAnnotatedStackOverflowObjectTwo {

	private Long id;

	private String name;
	
	@ApiObjectField(processtemplate = false)
	private NotAnnotatedStackOverflowObjectOne typeOne;

}
