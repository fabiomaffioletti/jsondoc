package org.jsondoc.core.pojo.global;

import java.util.UUID;

public class ApiGlobalHeaderDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private String headername;

	private String headervalue;

	private String description;

	public String getHeadername() {
		return headername;
	}

	public void setHeadername(String headername) {
		this.headername = headername;
	}

	public String getHeadervalue() {
		return headervalue;
	}

	public void setHeadervalue(String headervalue) {
		this.headervalue = headervalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
