package org.jsondoc.springmvc;

import org.jsondoc.core.pojo.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SpringJSONDocUtilsTest {

    public static final String BASE_PATH = "http://localhost:80";
    public static final String VERSION = "1.0";
    public static final String PACKAGE = "org.jsondoc.springmvc";

    @Test
    public void basePathAndVersionAreMapped() throws Exception {
        JSONDoc doc = SpringJSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));
        assertEquals(BASE_PATH, doc.getBasePath());
        assertEquals(VERSION, doc.getVersion());
    }

    @Test
    public void controllerValuesAreMapped()  {
        JSONDoc doc = SpringJSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        Map<String, Set<ApiDoc>> apis = doc.getApis();
        Set<ApiDoc> apiGroup = apis.get("api");
        assertEquals(1, apiGroup.size());

        ApiDoc firstDoc = apiGroup.iterator().next();
        assertEquals("sample", firstDoc.getName());
        assertEquals("manages samples", firstDoc.getDescription());
        assertEquals("api", firstDoc.getGroup());
    }

    @Test
    public void requestMapping()  {
        JSONDoc doc = SpringJSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        Map<String, Set<ApiDoc>> apis = doc.getApis();
        Set<ApiDoc> apiGroup = apis.get("api");
        assertEquals(1, apiGroup.size());

        ApiDoc firstDoc = apiGroup.iterator().next();
        List<ApiMethodDoc> methods = firstDoc.getMethods();

        ApiMethodDoc getSampleMethod = methods.get(0);
        assertEquals("retrieve sample by id", getSampleMethod.getDescription());
        assertEquals(ApiVerb.GET, getSampleMethod.getVerb());
        Set<ApiParamDoc> pathparameters = methods.get(0).getPathparameters();

        ApiParamDoc idPathVariable = pathparameters.iterator().next();
        assertEquals("id", idPathVariable.getName());
    }
}