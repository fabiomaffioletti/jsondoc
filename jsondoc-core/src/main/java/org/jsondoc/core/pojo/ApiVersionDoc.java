package org.jsondoc.core.pojo;

public class ApiVersionDoc {
	private String since;
	private String until;
	
	public ApiVersionDoc() {
		this.since = null;
		this.until = null;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

}
