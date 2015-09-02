package org.jsondoc.core.pojo.global;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class ApiGlobalSectionDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private String title;

	private Set<String> paragraphs;

	public ApiGlobalSectionDoc() {
		this.paragraphs = new LinkedHashSet<String>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(Set<String> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public void addParagraph(String paragraph) {
		this.paragraphs.add(paragraph);
	}

}
