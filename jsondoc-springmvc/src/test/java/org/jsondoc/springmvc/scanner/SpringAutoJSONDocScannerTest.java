package org.jsondoc.springmvc.scanner;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class SpringAutoJSONDocScannerTest extends SpringJSONDocScannerTest {

    @ApiObject(name="appform", group="application")
    @ApiVersion(since = "2.0")
    private class AppForm {

        @ApiObjectField(name="apps", description = "Application IDs", required = true)
        private List<String> apps = new ArrayList<String>(); //ids

    }

    private class ResponseBodyPojo {
        private String value1;
        private Integer value2;

        ResponseBodyPojo(String value1, Integer value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    @RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
    private class SpringControllerWithoutDocAnnotation {

        @RequestMapping(value = "/string/{name}", headers = "header=test", params = "delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
        @ResponseStatus(value = HttpStatus.CREATED)
        public
        @ResponseBody
        String string(
                @PathVariable(value = "test") String name,
                @RequestParam("id") Integer id,
                @RequestParam(value = "myquery") Long query,
                @RequestBody String requestBody) {
            return "ok";
        }

        @RequestMapping(value = "/responsestatuscodefromspringannotation")
        @ResponseStatus(value = HttpStatus.CONFLICT)
        public
        @ResponseBody
        String responsestatuscodefromspringannotation() {
            return "ok";
        }

        @RequestMapping(value = "/string/{name}/{surname}", params = "delete")
        public
        @ResponseBody
        String string(
                @PathVariable String name,
                @PathVariable("surname") String surname,
                @RequestParam("id") Integer id,
                @RequestParam Integer age) {
            return "ok";
        }

        @RequestMapping(value = "/pojo")
        public
        @ResponseBody
        ResponseBodyPojo pojo() {
            return new ResponseBodyPojo("value1", 5);
        }
    }

    @Before
    @Override
    public void initScanner() {
        jsondocScanner = new SpringAutoJSONDocScanner();
    }

    @Test
    public void testApiObjectDocs() {
        Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
        controllers.add(SpringControllerWithoutDocAnnotation.class);
        jsondocScanner.getApiDocs(controllers, JSONDoc.MethodDisplay.URI);
        SpringAutoJSONDocScanner autoJSONDocScanner = (SpringAutoJSONDocScanner) jsondocScanner;
        Map<String, Set<ApiObjectDoc>> objectDocs = autoJSONDocScanner.getNoAnnotationObjectDocSet();
        ApiObjectDoc objectDoc = (ApiObjectDoc) ((TreeSet) objectDocs.get("scanner")).first();
        Assert.assertEquals("responsebodypojo", objectDoc.getName());
        Assert.assertEquals("scanner", objectDoc.getGroup());

        boolean hasValue1 = false;
        boolean hasValue2 = false;
        for (Iterator iterator = objectDoc.getFields().iterator(); iterator.hasNext(); ) {
            ApiObjectFieldDoc doc = (ApiObjectFieldDoc) iterator.next();
            if (doc.getName().equals("value1")) {
                hasValue1 = true;
                Assert.assertEquals(doc.getJsondocType().getOneLineText(), "string");
            } else if (doc.getName().equals("value2")) {
                hasValue2 = true;
                Assert.assertEquals(doc.getJsondocType().getOneLineText(), "integer");
            }
        }
        Assert.assertTrue(hasValue1);
        Assert.assertTrue(hasValue2);
    }

    @Test
    public void testAnnotatedApiObjectDocs() {
        Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
        controllers.add(AppForm.class);
        jsondocScanner.getApiDocs(controllers, JSONDoc.MethodDisplay.URI);
        SpringAutoJSONDocScanner autoJSONDocScanner = (SpringAutoJSONDocScanner) jsondocScanner;
        Map<String, Set<ApiObjectDoc>> objectDocs = autoJSONDocScanner.getApiObjectsMap(controllers);
        ApiObjectDoc objectDoc = (ApiObjectDoc) ((TreeSet) objectDocs.get("application")).first();
        Assert.assertEquals("appform", objectDoc.getName());
        Assert.assertEquals("application", objectDoc.getGroup());
        Assert.assertEquals("2.0", objectDoc.getSupportedversions().getSince());

        boolean hasValue1 = false;
        for (Iterator iterator = objectDoc.getFields().iterator(); iterator.hasNext(); ) {
            ApiObjectFieldDoc doc = (ApiObjectFieldDoc) iterator.next();
            if (doc.getName().equals("apps")) {
                hasValue1 = true;
                Assert.assertEquals(doc.getJsondocType().getOneLineText(), "list of string");
                Assert.assertEquals(doc.getDescription(), "Application IDs");
            }
        }
        Assert.assertTrue(hasValue1);
    }

    @Test
    public void testApiMethodDocs() {
        Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
        controllers.add(SpringControllerWithoutDocAnnotation.class);
        Set<ApiDoc> apiDocs = jsondocScanner.getApiDocs(controllers, JSONDoc.MethodDisplay.URI);

        ApiDoc apiDoc = apiDocs.iterator().next();
        Assert.assertEquals("SpringControllerWithoutDocAnnotation", apiDoc.getName());

        boolean stringNameMappingExists = false;
        boolean stringNameSurnameMappingExists = false;
        boolean responsestatuscodefromspringannotationMappingExists = false;

        for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
            if (apiMethodDoc.getPath().equals("/api/string/{name}")) {
                stringNameMappingExists = true;

                Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getOneLineText());
                Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
                Assert.assertEquals("POST", apiMethodDoc.getVerb().name());
                Assert.assertEquals("application/json", apiMethodDoc.getProduces().iterator().next());
                Assert.assertEquals("application/json", apiMethodDoc.getConsumes().iterator().next());
                Assert.assertEquals("201 - Created", apiMethodDoc.getResponsestatuscode());

                Set<ApiHeaderDoc> headers = apiMethodDoc.getHeaders();
                ApiHeaderDoc header = headers.iterator().next();
                Assert.assertEquals("header", header.getName());
                Assert.assertEquals("test", header.getAllowedvalues()[0]);

                Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
                Assert.assertEquals(3, queryparameters.size());
                Iterator<ApiParamDoc> qpIterator = queryparameters.iterator();
                ApiParamDoc apiParamDoc = qpIterator.next();
                Assert.assertEquals("delete", apiParamDoc.getName());
                Assert.assertEquals("true", apiParamDoc.getRequired());
                Assert.assertEquals(null, apiParamDoc.getDefaultvalue());
                Assert.assertEquals(0, apiParamDoc.getAllowedvalues().length);
                apiParamDoc = qpIterator.next();
                Assert.assertEquals("id", apiParamDoc.getName());
                Assert.assertEquals("true", apiParamDoc.getRequired());
                Assert.assertEquals(ValueConstants.DEFAULT_NONE, apiParamDoc.getDefaultvalue());
                apiParamDoc = qpIterator.next();
                Assert.assertEquals("myquery", apiParamDoc.getName());
                Assert.assertEquals("true", apiParamDoc.getRequired());
                Assert.assertEquals(ValueConstants.DEFAULT_NONE, apiParamDoc.getDefaultvalue());

                apiParamDoc = apiMethodDoc.getPathparameters().iterator().next();
                Assert.assertEquals("test", apiParamDoc.getName());
            }

            if (apiMethodDoc.getPath().equals("/api/responsestatuscodefromspringannotation")) {
                responsestatuscodefromspringannotationMappingExists = true;
                Assert.assertEquals("409 - Conflict", apiMethodDoc.getResponsestatuscode());
            }

            if (apiMethodDoc.getPath().equals("/api/string/{name}/{surname}")) {
                stringNameSurnameMappingExists = true;

                Set<ApiParamDoc> pathparameters = apiMethodDoc.getPathparameters();
                Assert.assertEquals(2, pathparameters.size());

                Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
                Assert.assertEquals(3, queryparameters.size());

                Assert.assertEquals(0, apiMethodDoc.getJsondocerrors().size());
                Assert.assertEquals(0, apiMethodDoc.getJsondocwarnings().size());
                Assert.assertEquals(0, apiMethodDoc.getJsondochints().size());
            }
        }

        Assert.assertTrue(stringNameMappingExists);
        Assert.assertTrue(responsestatuscodefromspringannotationMappingExists);
        Assert.assertTrue(stringNameSurnameMappingExists);
    }
}
