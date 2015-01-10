package org.jsondoc.core.util;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DefaultJSONDocScannerTest {
    private String version = "1.0";
    private String basePath = "http://localhost:8080/api";
    private ObjectMapper objectMapper = new ObjectMapper();

    private static Logger log = Logger.getLogger(DefaultJSONDocScannerTest.class);

    @Test
    public void getJSONDoc() throws IOException {
    	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
        JSONDoc jsondoc = jsondocScanner.getJSONDoc(version, basePath, Lists.newArrayList("org.jsondoc.core.util"));
        assertEquals(1, jsondoc.getApis().size());

        int countApis = 0;
        for (String string : jsondoc.getApis().keySet()) {
            countApis += jsondoc.getApis().get(string).size();
        }
        assertEquals(2, countApis);

        assertEquals(2, jsondoc.getObjects().size());

        int countObjects = 0;
        for (String string : jsondoc.getObjects().keySet()) {
            countObjects += jsondoc.getObjects().get(string).size();
        }
        assertEquals(4, countObjects);

        Set<ApiVerb> apiVerbs = getAllTestedApiVerbs(jsondoc);
        assertEquals(ApiVerb.values().length, apiVerbs.size());

        log.debug(objectMapper.writeValueAsString(jsondoc));
    }

    private Set<ApiVerb> getAllTestedApiVerbs(JSONDoc jsondoc) {
        Set<ApiVerb> apiVerbs = new HashSet<ApiVerb>();

        for (String string : jsondoc.getObjects().keySet()) {
            Set<ApiDoc> apiDocs = jsondoc.getApis().get(string);
            if (apiDocs != null) {
                for (ApiDoc doc : apiDocs) {
                    for (ApiMethodDoc apiMethodDoc : doc.getMethods()) {
                        apiVerbs.add(apiMethodDoc.getVerb());
                    }
                }
            }
        }

        return apiVerbs;
    }

}