package org.jsondoc.core.pojo.global;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class ApiMigrationsDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private Set<ApiMigrationDoc> migrations;

	public ApiMigrationsDoc() {
		this.migrations = new LinkedHashSet<ApiMigrationDoc>();
	}

	public Set<ApiMigrationDoc> getMigrations() {
		return migrations;
	}

	public void setMigrations(Set<ApiMigrationDoc> migrations) {
		this.migrations = migrations;
	}

	public void addMigration(ApiMigrationDoc apiMigrationDoc) {
		this.migrations.add(apiMigrationDoc);
	}

}
