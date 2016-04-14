package org.jsondoc.core.pojo.global;

import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Sets;

public class ApiGlobalDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private Set<ApiGlobalSectionDoc> sections;

	private ApiChangelogsDoc changelogset;

	private ApiMigrationsDoc migrationset;

	public ApiGlobalDoc() {
		this.sections = Sets.newLinkedHashSet();
		this.changelogset = new ApiChangelogsDoc();
		this.migrationset = new ApiMigrationsDoc();
	}

	public Set<ApiGlobalSectionDoc> getSections() {
		return sections;
	}

	public void setSections(Set<ApiGlobalSectionDoc> sections) {
		this.sections = sections;
	}

	public ApiChangelogsDoc getChangelogset() {
		return changelogset;
	}

	public void setChangelogset(ApiChangelogsDoc changelogset) {
		this.changelogset = changelogset;
	}

	public ApiMigrationsDoc getMigrationset() {
		return migrationset;
	}

	public void setMigrationset(ApiMigrationsDoc migrationset) {
		this.migrationset = migrationset;
	}

	public void addApiGlobalSectionDoc(ApiGlobalSectionDoc apiGlobalSectionDoc) {
		this.sections.add(apiGlobalSectionDoc);
	}

}
