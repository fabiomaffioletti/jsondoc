package org.jsondoc.core.util.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject
public class HibernateValidatorPojo {
	
	@ApiObjectField(format = "a not empty id")
	@Length(min = 2)
	@Max(9)
	private String id;

	@ApiObjectField
	@NotNull
	private String notNullField;

	@ApiObjectField
	@NotEmpty
	private String notEmptyField;

	@ApiObjectField
	@NotBlank
	private String notBlankField;
}
