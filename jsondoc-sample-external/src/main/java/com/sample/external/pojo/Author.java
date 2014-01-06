package com.sample.external.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "author", description = "An author object belonging to an external jar")
public class Author {

	@ApiObjectField(description = "The author's id")
	private Integer id;
	@ApiObjectField(description = "The author's name")
	private String name;
	@ApiObjectField(description = "The author's surname")
	private String surname;

	public Author(Integer id, String name, String surname) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", surname=" + surname + "]";
	}

}
