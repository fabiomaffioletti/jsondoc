package org.jsondoc.core.util.pojo;

import org.jsondoc.core.annotation.ApiObjectField;

public class StackOverflowTemplateSelf {
	
	@ApiObjectField
	private Integer id;
	
	@ApiObjectField
	private StackOverflowTemplateSelf ooo;
	
}
