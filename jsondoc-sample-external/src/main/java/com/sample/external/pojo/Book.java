package com.sample.external.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "book", description = "A book object belonging to an external jar")
public class Book {

	@ApiObjectField(description = "The book's id")
	private Integer id;
	@ApiObjectField(description = "The book's title")
	private String name;
	@ApiObjectField(description = "The book's author")
	private Author author;

	public Book(Integer id, String name, Author author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
	}

}
