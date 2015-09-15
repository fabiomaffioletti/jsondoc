package org.jsondoc.core.util.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.annotation.ApiObjectField;

public class TemplateObject {
	
	@ApiObjectField(name = "my_id")
	private Integer id;

	@ApiObjectField
	private String name;
	
	@ApiObjectField
	private Gender gender;
	
	@ApiObjectField
	private boolean bool;
	
	@ApiObjectField
	private int idint;
	
	@ApiObjectField
	private long idlong;

	@ApiObjectField
	private char namechar;

	@ApiObjectField
	private String[] stringarr;

	@ApiObjectField
	private Integer[] integerarr;

	@ApiObjectField
	private List untypedlist;
	
	@ApiObjectField
	private List<?> wildcardlist;

	@ApiObjectField
	private List<Long> longlist;

	@ApiObjectField
	private List<String> stringlist;

	@ApiObjectField(name = "sub_obj", processtemplate = false)
	private TemplateSubObject subObj = new TemplateSubObject();
	
	@ApiObjectField
	private List<TemplateSubObject> subobjlist = new ArrayList<TemplateSubObject>();
	
	@ApiObjectField
	private int[] intarr;
	
	@ApiObjectField
	private int[][] intarrarr;
	
	@ApiObjectField
	private String[][] stringarrarr;
	
	@ApiObjectField
	private TemplateSubSubObject[] subsubobjarr;
	
	@ApiObjectField
	private Map map;
	
	@ApiObjectField
	private Map<String, Integer> mapstringinteger;
	
	@ApiObjectField
	private Map<TemplateSubObject, Integer> mapsubobjinteger;

	@ApiObjectField
	private Map<String, TemplateSubObject> mapintegersubobj;
	
	@ApiObjectField
	private Map<String, List<TemplateSubObject>> mapintegerlistsubsubobj;
	
}