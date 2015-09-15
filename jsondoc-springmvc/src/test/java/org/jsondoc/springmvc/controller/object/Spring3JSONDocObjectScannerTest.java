package org.jsondoc.springmvc.controller.object;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.Spring3JSONDocScanner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class Spring3JSONDocObjectScannerTest {
    private String version = "1.0";
    private String basePath = "http://localhost:8080/api";

    private static Logger log = LoggerFactory.getLogger(Spring3JSONDocObjectScannerTest.class);
    
    @Test
    public void getJSONDoc() throws IOException {
    	JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();
        JSONDoc jsondoc = jsondocScanner.getJSONDoc(version, basePath, Lists.newArrayList("org.jsondoc.springmvc.controller"), true, MethodDisplay.URI);

        Map<String, Set<ApiObjectDoc>> objects = jsondoc.getObjects();
        for (Set<ApiObjectDoc> values : objects.values()) {
			for (ApiObjectDoc apiObjectDoc : values) {
				System.out.println(apiObjectDoc.getName());
			}
		}
        
    }

}