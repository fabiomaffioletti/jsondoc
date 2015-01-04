package org.jsondoc.springmvc;

import org.jsondoc.core.pojo.*;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class SpringJSONDocUtilsTest {

    public static final String BASE_PATH = "http://localhost:80";
    public static final String VERSION = "1.0";
    public static final String PACKAGE = "org.jsondoc.springmvc";

    @Test
    public void basePathAndVersionAreMapped() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));
        assertEquals(BASE_PATH, doc.getBasePath());
        assertEquals(VERSION, doc.getVersion());
    }

    @Test
    public void objectsAreMappedJustLikeTheyUsedToBe() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));
        Set<ApiObjectDoc> sample = doc.getObjects().get("");
        assertEquals(1, sample.size());
        ApiObjectDoc sampleObject = sample.iterator().next();
        assertEquals("sample", sampleObject.getName());
        assertEquals("a sample", sampleObject.getDescription());
        assertEquals(4, sampleObject.getFields().size());
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
        assertEquals(sampleDoc.getMethods().size(), 9);
    }

    @Test
    public void producesAndConsumesAreMapped() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc getSampleById = findMethod(sampleDoc, "/samples/{id}", ApiVerb.GET);

        assertTrue(getSampleById.getConsumes().containsAll(asList("application/json", "application/xml")));
    }

    @Test
    public void customVersionsAreMapped() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc headerRequest = findMethod(sampleDoc, "/samplesCustomVersion", ApiVerb.GET);
        assertSupported("1.1", "1.1", headerRequest);
    }

    @Test
    public void requestMappingWithHeaders() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc headerRequest = findMethod(sampleDoc, "/samplesWithHeaders", ApiVerb.GET);
        assertSupported("1.0", "", headerRequest);

        List<ApiHeaderDoc> headers = headerRequest.getHeaders();
        assertEquals(2, headers.size());
        assertEquals(headers.get(0).getName(), "one");
        assertEquals(headers.get(1).getName(), "two");
    }

    @Test
    public void requestMappingWithUndefinedMethodGetsTranslatedToGet() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc method = findMethod(sampleDoc, "/samplesWithoutMethod", ApiVerb.GET);
        assertEquals("sample without method", method.getDescription());
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

        assertEquals("sample", getSampleById.getResponse().getJsondocType().getType());
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

        assertEquals("sample", getSampleById.getResponse().getJsondocType().getType());
    }

    @Test
    public void requestMappingWithRequestBody() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc createSample = findMethod(sampleDoc, "/samples", ApiVerb.POST);

        assertEquals("create sample", createSample.getDescription());
        ApiBodyObjectDoc bodyobject = createSample.getBodyobject();
        JSONDocType jsondocType = bodyobject.getJsondocType();
        assertEquals("sample", jsondocType.getType());

        assertEquals("sample", createSample.getResponse().getJsondocType().getType());
    }

    @Test
    public void requestMappingWithCollectionResponseType() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc createSample = findMethod(sampleDoc, "/allsamples", ApiVerb.GET);

        assertEquals("get all samples", createSample.getDescription());
        assertEquals("list of sample", createSample.getResponse().getJsondocType().getType());
    }

    @Test
    public void requestMappingWithResponseEntityCollectionResponseType() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc createSample = findMethod(sampleDoc, "/allsamplesWithEntities", ApiVerb.GET);

        assertEquals("get all samples with response entity", createSample.getDescription());
        assertEquals("list of sample", createSample.getResponse().getJsondocType().getType());
    }

    @Test
    public void requestMappingWithResponseEntitySampleResponseType() {
        JSONDoc doc = JSONDocUtils.getApiDoc(VERSION, BASE_PATH, asList(PACKAGE));

        ApiDoc sampleDoc = getApiDoc(doc, "api", "sample");
        ApiMethodDoc createSample = findMethod(sampleDoc, "/samplesEntity", ApiVerb.GET);

        assertEquals("get sample with response entity", createSample.getDescription());
        assertEquals("sample", createSample.getResponse().getJsondocType().getType());
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

    private void assertSupported(String since, String until, ApiMethodDoc headerRequest) {
        ApiVersionDoc supportedversions = headerRequest.getSupportedversions();
        assertEquals(supportedversions.getSince(), since);
        assertEquals(supportedversions.getUntil(), until);
    }
}