package org.jsondoc.core.scanner.builder;

import java.util.Iterator;

import org.jsondoc.core.annotation.global.ApiChangelog;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.annotation.global.ApiMigration;
import org.jsondoc.core.pojo.global.ApiChangelogDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;
import org.jsondoc.core.pojo.global.ApiMigrationDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class JSONDocApiGlobalBuilderTest {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@ApiGlobal
	private class GlobalEmpty {
		
	}
	
	@ApiGlobal(
		changelogs = {
			@ApiChangelog(changes = { "First changelog", "Second changelog" }, version = "1.0"),
			@ApiChangelog(changes = { "Third changelog" }, version = "1.1")
		},
		migrations = {
			@ApiMigration(fromversion = "1.0", steps = { "Add this to that", "Change these to those" }, toversion = "1.1")
		}
	)
	private class GlobalWithData {
		
	}
	
	@Test
	public void testApiVisibility() {
		ApiGlobalDoc apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>> newHashSet(GlobalEmpty.class));
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertTrue(apiGlobalDoc.getChangelogs().isEmpty());
		Assert.assertTrue(apiGlobalDoc.getMigrations().isEmpty());
		
		apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>> newHashSet(GlobalWithData.class));
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(2, apiGlobalDoc.getChangelogs().size());
		Assert.assertEquals(1, apiGlobalDoc.getMigrations().size());
		
		Iterator<ApiChangelogDoc> changelogs = apiGlobalDoc.getChangelogs().iterator();
		ApiChangelogDoc apiChangelogDoc = changelogs.next();
		Assert.assertEquals("1.0", apiChangelogDoc.getVersion());
		Assert.assertEquals(2, apiChangelogDoc.getChanges().length);
		apiChangelogDoc = changelogs.next();
		Assert.assertEquals("1.1", apiChangelogDoc.getVersion());
		Assert.assertEquals(1, apiChangelogDoc.getChanges().length);
		
		Iterator<ApiMigrationDoc> migrations = apiGlobalDoc.getMigrations().iterator();
		ApiMigrationDoc apiMigrationDoc = migrations.next();
		Assert.assertEquals("1.0", apiMigrationDoc.getFromVersion());
		Assert.assertEquals("1.1", apiMigrationDoc.getToVersion());
		Assert.assertEquals(2, apiMigrationDoc.getSteps().length);
	}

}
