package org.jsondoc.core.scanner.builder;

import org.jsondoc.core.annotation.global.ApiChangelog;
import org.jsondoc.core.annotation.global.ApiChangelogSet;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.annotation.global.ApiGlobalSection;
import org.jsondoc.core.annotation.global.ApiMigration;
import org.jsondoc.core.annotation.global.ApiMigrationSet;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;
import org.jsondoc.core.pojo.global.ApiGlobalSectionDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class JSONDocApiGlobalBuilderTest {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@ApiGlobal(
		sections = { 
			@ApiGlobalSection(title = "title", paragraphs = {
				"Paragraph 1", "Paragraph 2", "/jsondocfile./src/main/resources/text.txt" }
			) 
		}
	)
	private class Global {
		
	}
	
	@ApiGlobal(
		sections = { 
			@ApiGlobalSection(title = "section1", paragraphs = { "Paragraph 1" }), 
			@ApiGlobalSection(title = "abc", paragraphs = { "Paragraph 1", "Paragraph2" }), 
			@ApiGlobalSection(title = "198xyz", paragraphs = { "Paragraph 1", "Paragraph2", "Paragraph3", "Paragraph4" }), 
		}
	)
	private class MultipleGlobalSections {
		
	}
	
	
	@ApiChangelogSet(changlogs = { @ApiChangelog(changes = { "Change #1" }, version = "1.0") })
	private class Changelog {
		
	}
	
	@ApiMigrationSet(migrations = { @ApiMigration(fromversion = "1.0", steps = { "Step #1" }, toversion = "1.1") })
	private class Migration {
		
	}
	
	@ApiGlobal(
		sections = { 
			@ApiGlobalSection(title = "title", paragraphs = {
				"Paragraph 1", "Paragraph 2", "/jsondocfile./src/main/resources/text.txt" }
			) 
		}
	)
	@ApiChangelogSet(changlogs = { @ApiChangelog(changes = { "Change #1" }, version = "1.0") })
	@ApiMigrationSet(migrations = { @ApiMigration(fromversion = "1.0", steps = { "Step #1" }, toversion = "1.1") })
	private class AllTogether {
		
	}
	
	@Test
	public void testApiGlobalDoc() {
		ApiGlobalDoc apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>>newHashSet(Global.class), Sets.<Class<?>>newHashSet(), Sets.<Class<?>>newHashSet());
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(1, apiGlobalDoc.getSections().size());
		ApiGlobalSectionDoc sectionDoc = apiGlobalDoc.getSections().iterator().next();
		Assert.assertEquals("title", sectionDoc.getTitle());
		Assert.assertEquals(3, sectionDoc.getParagraphs().size());
		
		apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>>newHashSet(), Sets.<Class<?>>newHashSet(Changelog.class), Sets.<Class<?>>newHashSet());
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(1, apiGlobalDoc.getChangelogset().getChangelogs().size());

		apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>>newHashSet(MultipleGlobalSections.class), Sets.<Class<?>>newHashSet(), Sets.<Class<?>>newHashSet());
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(3, apiGlobalDoc.getSections().size());
		
		ApiGlobalSectionDoc[] apiGlobalSectionDocs = apiGlobalDoc.getSections().toArray(new ApiGlobalSectionDoc[apiGlobalDoc.getSections().size()]);
		Assert.assertEquals("section1", apiGlobalSectionDocs[0].getTitle());
		Assert.assertEquals("abc", apiGlobalSectionDocs[1].getTitle());
		Assert.assertEquals("198xyz", apiGlobalSectionDocs[2].getTitle());
		
		apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>>newHashSet(), Sets.<Class<?>>newHashSet(), Sets.<Class<?>>newHashSet(Migration.class));
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(1, apiGlobalDoc.getMigrationset().getMigrations().size());
		
		apiGlobalDoc = jsondocScanner.getApiGlobalDoc(Sets.<Class<?>>newHashSet(AllTogether.class), Sets.<Class<?>>newHashSet(AllTogether.class), Sets.<Class<?>>newHashSet(AllTogether.class));
		Assert.assertNotNull(apiGlobalDoc);
		Assert.assertEquals(1, apiGlobalDoc.getSections().size());
		Assert.assertEquals(1, apiGlobalDoc.getMigrationset().getMigrations().size());
		Assert.assertEquals(1, apiGlobalDoc.getChangelogset().getChangelogs().size());
	}

}
