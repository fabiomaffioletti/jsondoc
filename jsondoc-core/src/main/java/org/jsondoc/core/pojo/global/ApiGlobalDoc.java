package org.jsondoc.core.pojo.global;

import java.util.LinkedHashSet;
import java.util.Set;

public class ApiGlobalDoc {

	private Set<ApiGlobalVerbDoc> globalverbs;

	private Set<ApiGlobalHeaderDoc> globalheaders;

	private Set<ApiGlobalResponseStatusCodeDoc> globalresponsestatuscodes;

	private Set<ApiChangelogDoc> changelogs;

	private Set<ApiMigrationDoc> migrations;

	public ApiGlobalDoc() {
		globalverbs = new LinkedHashSet<ApiGlobalVerbDoc>();
		globalheaders = new LinkedHashSet<ApiGlobalHeaderDoc>();
		globalresponsestatuscodes = new LinkedHashSet<ApiGlobalResponseStatusCodeDoc>();
		changelogs = new LinkedHashSet<ApiChangelogDoc>();
		migrations = new LinkedHashSet<ApiMigrationDoc>();
	}

	public Set<ApiChangelogDoc> getChangelogs() {
		return changelogs;
	}

	public void setChangelogs(Set<ApiChangelogDoc> changelogs) {
		this.changelogs = changelogs;
	}

	public Set<ApiMigrationDoc> getMigrations() {
		return migrations;
	}

	public void setMigrations(Set<ApiMigrationDoc> migrations) {
		this.migrations = migrations;
	}

	public Set<ApiGlobalVerbDoc> getGlobalverbs() {
		return globalverbs;
	}

	public void setGlobalverbs(Set<ApiGlobalVerbDoc> globalverbs) {
		this.globalverbs = globalverbs;
	}

	public Set<ApiGlobalResponseStatusCodeDoc> getGlobalresponsestatuscodes() {
		return globalresponsestatuscodes;
	}

	public void setGlobalresponsestatuscodes(Set<ApiGlobalResponseStatusCodeDoc> globalresponsestatuscodes) {
		this.globalresponsestatuscodes = globalresponsestatuscodes;
	}

	public Set<ApiGlobalHeaderDoc> getGlobalheaders() {
		return globalheaders;
	}

	public void setGlobalheaders(Set<ApiGlobalHeaderDoc> globalheaders) {
		this.globalheaders = globalheaders;
	}

	public void addChangelog(ApiChangelogDoc apiChangelogDoc) {
		this.changelogs.add(apiChangelogDoc);
	}

	public void addMigration(ApiMigrationDoc apiMigrationDoc) {
		this.migrations.add(apiMigrationDoc);
	}

	public void addGlobalverb(ApiGlobalVerbDoc apiGlobalVerbDoc) {
		this.globalverbs.add(apiGlobalVerbDoc);
	}

	public void addGlobalresponsestatuscode(ApiGlobalResponseStatusCodeDoc apiGlobalResponseStatusCodeDoc) {
		this.globalresponsestatuscodes.add(apiGlobalResponseStatusCodeDoc);
	}
	
	public void addGlobalheader(ApiGlobalHeaderDoc apiGlobalHeaderDoc) {
		this.globalheaders.add(apiGlobalHeaderDoc);
	}

}
