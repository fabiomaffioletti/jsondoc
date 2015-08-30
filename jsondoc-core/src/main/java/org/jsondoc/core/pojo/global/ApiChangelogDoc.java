package org.jsondoc.core.pojo.global;

import java.util.UUID;

public class ApiChangelogDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private String version;

	private String[] changes;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String[] getChanges() {
		return changes;
	}

	public void setChanges(String[] changes) {
		this.changes = changes;
	}

}
