package org.jsondoc.core.pojo.global;

import java.util.UUID;

public class ApiMigrationDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private String fromVersion;

	private String toVersion;

	private String[] steps;

	public String getFromVersion() {
		return fromVersion;
	}

	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}

	public String getToVersion() {
		return toVersion;
	}

	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}

	public String[] getSteps() {
		return steps;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}

}
