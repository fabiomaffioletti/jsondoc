package org.jsondoc.springmvc;

import org.jsondoc.core.pojo.*;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SpringJSONDocUtilsTest {

    public static final String BASE_PATH = "http://localhost:80";
    public static final String VERSION = "1.0";
    public static final String PACKAGE = "org.jsondoc.springmvc";

    @Test
    public void basePathAndVersionAreMapped() throws Exception {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));
        assertEquals(BASE_PATH, doc.getBasePath());
        assertEquals(VERSION, doc.getVersion());
    }

    @Test
    public void controllerValuesAreMapped() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        Map<String, Set<ApiDoc>> apis = doc.getApis();
        Set<ApiDoc> apiGroup = apis.get("api");
        assertEquals(1, apiGroup.size());

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");

        assertEquals("manages samples", sampleDoc.getDescription());
        assertEquals("api", sampleDoc.getGroup());
    }

    @Test
    public void allMethodsAreFound() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));
        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        assertEquals(sampleDoc.getMethods().size(), 2);
    }

    @Test
    public void requestMappingWithPathVariable() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc getSampleById = findMethod(sampleDoc, "/samples/{id}", ApiVerb.GET);

        assertEquals("retrieve sample by id", getSampleById.getDescription());
        Set<ApiParamDoc> pathVariables = getSampleById.getPathparameters();
        ApiParamDoc idPathVariable = pathVariables.iterator().next();
        assertEquals("id", idPathVariable.getName());
        assertEquals("true", idPathVariable.getRequired());
    }

    @Test
    public void requestMappingWithRequestParam() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc getSampleById = findMethod(sampleDoc, "/samples", ApiVerb.GET);

        assertEquals("retrieve sample by id", getSampleById.getDescription());
        Set<ApiParamDoc> queryParams = getSampleById.getQueryparameters();
        ApiParamDoc idParam = queryParams.iterator().next();
        assertEquals("id", idParam.getName());
        assertEquals("true", idParam.getRequired());
    }


    private ApiMethodDoc findMethod(ApiDoc sampleDoc, String path, ApiVerb verb) {
        for (ApiMethodDoc method : sampleDoc.getMethods()) {
            if (method.getVerb().equals(verb) && method.getPath().equals(path)) {
                return method;
            }
        }

        fail(String.format("Unable to locate method \"%s\":\"%s\"", path, verb));
        return null;
    }

    private ApiDoc getApiDoc(JSONDoc doc, String groupName, String apiDocName) {
        Map<String, Set<ApiDoc>> apis = doc.getApis();

        for (String s : apis.keySet()) {
            if (groupName.equals(s)) {
                for (ApiDoc apiDoc : apis.get(s)) {
                    if (apiDoc.getName().equals(apiDocName)) {
                        return apiDoc;
                    }
                }
            }
        }

        fail(String.format("Unable to locate api doc \"%s\":\"%s\"", groupName, apiDocName));
        return null;
    }
}