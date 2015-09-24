package org.jsondoc.core.scanner.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import org.jsondoc.core.annotation.global.ApiChangelog;
import org.jsondoc.core.annotation.global.ApiChangelogSet;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.annotation.global.ApiGlobalSection;
import org.jsondoc.core.annotation.global.ApiMigration;
import org.jsondoc.core.annotation.global.ApiMigrationSet;
import org.jsondoc.core.pojo.global.ApiChangelogDoc;
import org.jsondoc.core.pojo.global.ApiChangelogsDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;
import org.jsondoc.core.pojo.global.ApiGlobalSectionDoc;
import org.jsondoc.core.pojo.global.ApiMigrationDoc;
import org.jsondoc.core.pojo.global.ApiMigrationsDoc;

public class JSONDocApiGlobalDocBuilder {
	
	public static ApiGlobalDoc build(Set<Class<?>> globalClasses, Set<Class<?>> changelogClasses, Set<Class<?>> migrationClasses) {
		ApiGlobalDoc apiGlobalDoc = new ApiGlobalDoc();
		apiGlobalDoc = buildGlobalDoc(apiGlobalDoc, globalClasses);
		apiGlobalDoc = buildChangelogDoc(apiGlobalDoc, changelogClasses);
		apiGlobalDoc = buildMigrationDoc(apiGlobalDoc, migrationClasses);
		if(apiGlobalDoc.getSections().isEmpty() && apiGlobalDoc.getChangelogset().getChangelogs().isEmpty() && apiGlobalDoc.getMigrationset().getMigrations().isEmpty()) {
			return null;
		} else {
			return apiGlobalDoc;
		}
	}
	
	private static ApiGlobalDoc buildGlobalDoc(ApiGlobalDoc apiGlobalDoc, Set<Class<?>> globalClasses) {
		if(!globalClasses.isEmpty()) {
			Class<?> clazz = globalClasses.iterator().next();
			ApiGlobal apiGlobal = clazz.getAnnotation(ApiGlobal.class);
			
			for (ApiGlobalSection section : apiGlobal.sections()) {
				ApiGlobalSectionDoc sectionDoc = new ApiGlobalSectionDoc();
				sectionDoc.setTitle(section.title());
				for (String paragraph : section.paragraphs()) {
					if(paragraph.startsWith("/jsondocfile:")) {
						String path = paragraph.replace("/jsondocfile:", "");
						try {
							
							InputStream resourceAsStream = JSONDocApiGlobalDocBuilder.class.getResourceAsStream(path);
							if(resourceAsStream != null) {
								String content = getStringFromInputStream(resourceAsStream);
								sectionDoc.addParagraph(content);
								
							} else {
								sectionDoc.addParagraph("Unable to find file in path: " + path);
							}
							
						} catch (IOException e) {
							sectionDoc.addParagraph("Unable to find file in path: " + path);
						}
						
					} else {
						sectionDoc.addParagraph(paragraph);
					}
				}
				apiGlobalDoc.addApiGlobalSectionDoc(sectionDoc);
			}
		}
		return apiGlobalDoc;
	}
	
	private static ApiGlobalDoc buildChangelogDoc(ApiGlobalDoc apiGlobalDoc, Set<Class<?>> changelogClasses) {
		if(!changelogClasses.isEmpty()) {
			Class<?> clazz = changelogClasses.iterator().next();
			ApiChangelogSet apiChangelogSet = clazz.getAnnotation(ApiChangelogSet.class);
			ApiChangelogsDoc apiChangelogsDoc = new ApiChangelogsDoc();
			for (ApiChangelog apiChangelog : apiChangelogSet.changlogs()) {
				ApiChangelogDoc apiChangelogDoc = new ApiChangelogDoc();
				apiChangelogDoc.setVersion(apiChangelog.version());
				apiChangelogDoc.setChanges(apiChangelog.changes());
				apiChangelogsDoc.addChangelog(apiChangelogDoc);
			}
			apiGlobalDoc.setChangelogset(apiChangelogsDoc);
		}
		return apiGlobalDoc;
	}
	
	private static ApiGlobalDoc buildMigrationDoc(ApiGlobalDoc apiGlobalDoc, Set<Class<?>> migrationClasses) {
		if(!migrationClasses.isEmpty()) {
			Class<?> clazz = migrationClasses.iterator().next();
			ApiMigrationSet apiMigrationSet = clazz.getAnnotation(ApiMigrationSet.class);
			ApiMigrationsDoc apiMigrationsDoc = new ApiMigrationsDoc();
			for (ApiMigration apiMigration : apiMigrationSet.migrations()) {
				ApiMigrationDoc apiMigrationDoc = new ApiMigrationDoc();
				apiMigrationDoc.setFromVersion(apiMigration.fromversion());
				apiMigrationDoc.setToVersion(apiMigration.toversion());
				apiMigrationDoc.setSteps(apiMigration.steps());
				apiMigrationsDoc.addMigration(apiMigrationDoc);
			}
			apiGlobalDoc.setMigrationset(apiMigrationsDoc);
		}
		return apiGlobalDoc;
	}
	
	private static String getStringFromInputStream(InputStream is) throws IOException {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return sb.toString();
	}
	
}
