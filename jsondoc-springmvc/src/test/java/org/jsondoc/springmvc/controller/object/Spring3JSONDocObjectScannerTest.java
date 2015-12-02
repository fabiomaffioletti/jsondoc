package org.jsondoc.springmvc.controller.object;

import com.google.common.collect.Lists;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.Spring3JSONDocScanner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.fail;

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

    @Test
    public void findsNestedObject() throws Exception {
        JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();
        JSONDoc jsondoc = jsondocScanner.getJSONDoc(version, basePath, Lists.newArrayList("org.jsondoc.springmvc.controller"), true, MethodDisplay.URI);

        Map<String, Set<ApiObjectDoc>> objects = jsondoc.getObjects();
        for (Set<ApiObjectDoc> values : objects.values()) {
            assertContainsDoc(values, "NestedObject1");
        }
    }

    @Test
    public void findsDeeplyNestedObjects() throws Exception {
        JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();
        JSONDoc jsondoc = jsondocScanner.getJSONDoc(version, basePath, Lists.newArrayList("org.jsondoc.springmvc.controller"), true, MethodDisplay.URI);

        Map<String, Set<ApiObjectDoc>> objects = jsondoc.getObjects();
        for (Set<ApiObjectDoc> values : objects.values()) {
            assertContainsDoc(values, "NestedObject2");
            assertContainsDoc(values, "NestedObject3");
        }
    }


    public void assertContainsDoc(Set<ApiObjectDoc> values, String name) {
        for (ApiObjectDoc apiObjectDoc : values) {
            if(apiObjectDoc.getName().equals(name)) {
                return;
            }
        }
        fail("Could not find ApiObjectDoc with name " + name);
    }

}