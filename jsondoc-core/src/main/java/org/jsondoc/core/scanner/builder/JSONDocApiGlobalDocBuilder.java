package org.jsondoc.core.scanner.builder;

import java.util.Set;

import org.jsondoc.core.annotation.global.ApiChangelog;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.annotation.global.ApiGlobalHeader;
import org.jsondoc.core.annotation.global.ApiGlobalResponseStatusCode;
import org.jsondoc.core.annotation.global.ApiGlobalVerb;
import org.jsondoc.core.annotation.global.ApiMigration;
import org.jsondoc.core.pojo.global.ApiChangelogDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;
import org.jsondoc.core.pojo.global.ApiGlobalHeaderDoc;
import org.jsondoc.core.pojo.global.ApiGlobalResponseStatusCodeDoc;
import org.jsondoc.core.pojo.global.ApiGlobalVerbDoc;
import org.jsondoc.core.pojo.global.ApiMigrationDoc;

public class JSONDocApiGlobalDocBuilder {
	
	public static ApiGlobalDoc build(Set<Class<?>> classes) {
		if(classes.isEmpty()) {
			return null;
		} else {
			Class<?> clazz = classes.iterator().next();
			ApiGlobal apiGlobal = clazz.getAnnotation(ApiGlobal.class);
			ApiGlobalDoc apiGlobalDoc = new ApiGlobalDoc();
			
			for (ApiGlobalVerb apiGlobalVerb : apiGlobal.globalverbs()) {
				ApiGlobalVerbDoc apiGlobalVerbDoc = new ApiGlobalVerbDoc();
				apiGlobalVerbDoc.setVerb(apiGlobalVerb.verb());
				apiGlobalVerbDoc.setDescription(apiGlobalVerb.description());
				apiGlobalDoc.addGlobalverb(apiGlobalVerbDoc);
			}
			
			for (ApiGlobalHeader apiGlobalHeader : apiGlobal.globalheaders()) {
				ApiGlobalHeaderDoc apiGlobalHeaderDoc = new ApiGlobalHeaderDoc();
				apiGlobalHeaderDoc.setHeadername(apiGlobalHeader.headername());
				apiGlobalHeaderDoc.setHeadervalue(apiGlobalHeader.headervalue());
				apiGlobalHeaderDoc.setDescription(apiGlobalHeader.description());
				apiGlobalDoc.addGlobalheader(apiGlobalHeaderDoc);
			}
			
			for (ApiGlobalResponseStatusCode apiGlobalResponseStatusCode : apiGlobal.globalresponsestatuscodes()) {
				ApiGlobalResponseStatusCodeDoc apiGlobalResponseStatusCodeDoc = new ApiGlobalResponseStatusCodeDoc();
				apiGlobalResponseStatusCodeDoc.setCode(apiGlobalResponseStatusCode.code());
				apiGlobalResponseStatusCodeDoc.setDescription(apiGlobalResponseStatusCode.description());
				apiGlobalDoc.addGlobalresponsestatuscode(apiGlobalResponseStatusCodeDoc);
			}

			for (ApiChangelog apiChangelog : apiGlobal.changelogs()) {
				ApiChangelogDoc apiChangelogDoc = new ApiChangelogDoc();
				apiChangelogDoc.setVersion(apiChangelog.version());
				apiChangelogDoc.setChanges(apiChangelog.changes());
				apiGlobalDoc.addChangelog(apiChangelogDoc);
			}
			
			for (ApiMigration apiMigration : apiGlobal.migrations()) {
				ApiMigrationDoc apiMigrationDoc = new ApiMigrationDoc();
				apiMigrationDoc.setFromVersion(apiMigration.fromversion());
				apiMigrationDoc.setToVersion(apiMigration.toversion());
				apiMigrationDoc.setSteps(apiMigration.steps());
				apiGlobalDoc.addMigration(apiMigrationDoc);
			}
			
			return apiGlobalDoc;	
		}
	}
	
}
