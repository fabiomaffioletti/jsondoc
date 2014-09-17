package org.jsondoc.sample.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	@ApiObjectField(description = "The ID of the user")
	@XmlElement(name = "id")
	private Integer id;
	@ApiObjectField(description = "The username of the user", required = true)
	@XmlElement(name = "username")
	private String username;
	@ApiObjectField(description = "The age of the user", required = true)
	@XmlElement(name = "age")
	private Integer age;
	@ApiObjectField(description = "The gender of the user", required = false)
	@XmlElement(name = "gender")
	private String gender;

	public User() {

	}

	public User(Integer id, String username, Integer age, String gender) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
