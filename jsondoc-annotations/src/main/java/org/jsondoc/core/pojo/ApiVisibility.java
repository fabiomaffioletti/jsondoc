package org.jsondoc.core.pojo;

public enum ApiVisibility {
	UNDEFINED(""), PRIVATE("PRIVATE"), PUBLIC("PUBLIC");

	private String label;

	ApiVisibility(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}