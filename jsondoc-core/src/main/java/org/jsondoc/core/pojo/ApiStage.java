package org.jsondoc.core.pojo;

public enum ApiStage {
	UNDEFINED(""), PRE_ALPHA("PRE-ALPHA"), ALPHA("ALPHA"), BETA("BETA"), RC("RC"), GA("GA"), DEPRECATED("DEPRECATED");

	private String label;

	ApiStage(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}