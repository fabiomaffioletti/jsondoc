package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDoc {

	protected List<String> jsondocerrors;
	protected List<String> jsondocwarnings;
	protected List<String> jsondochints;
	
	public AbstractDoc() {
		this.jsondocerrors = new ArrayList<String>();
		this.jsondocwarnings = new ArrayList<String>();
		this.jsondochints = new ArrayList<String>();
	}

	public List<String> getJsondocerrors() {
		return jsondocerrors;
	}

	public void setJsondocerrors(List<String> jsondocerrors) {
		this.jsondocerrors = jsondocerrors;
	}

	public List<String> getJsondocwarnings() {
		return jsondocwarnings;
	}

	public void setJsondocwarnings(List<String> jsondocwarnings) {
		this.jsondocwarnings = jsondocwarnings;
	}

	public List<String> getJsondochints() {
		return jsondochints;
	}

	public void setJsondochints(List<String> jsondochints) {
		this.jsondochints = jsondochints;
	}

	public void addJsondocerror(String jsondocerror) {
		this.jsondocerrors.add(jsondocerror);
	}

	public void addJsondocwarning(String jsondocwarning) {
		this.jsondocwarnings.add(jsondocwarning);
	}

	public void addJsondochint(String jsondochint) {
		this.jsondochints.add(jsondochint);
	}

}
