package org.jsondoc.springmvc.scanner;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class SpringRequestMappingDerivativesTest {

    private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

    @Controller
    public class RequestMappingController {

        @GetMapping(value = "/request")
        public void get() {

        }

        @PostMapping(value = "/request")
        public void post() {

        }

        @PutMapping(value = "/request")
        public void put() {

        }

        @PatchMapping(value = "/request")
        public void patch() {

        }

        @DeleteMapping(value = "/request")
        public void delete() {

        }
    }


    @Test
    public void testGetMapping() {
        ApiDoc
            apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(RequestMappingController.class), MethodDisplay.URI).iterator().next();
        Assert.assertEquals("RequestMappingController", apiDoc.getName());

        boolean getMethodPresent = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
            @Override
            public boolean apply(ApiMethodDoc input) {
                return input.getMethod().equals("get");
            }
        });
        Assert.assertTrue(getMethodPresent);

        boolean postMethodPresent = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
            @Override
            public boolean apply(ApiMethodDoc input) {
                return input.getMethod().equals("post");
            }
        });
        Assert.assertTrue(postMethodPresent);

        boolean putMethodPresent = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
            @Override
            public boolean apply(ApiMethodDoc input) {
                return input.getMethod().equals("put");
            }
        });
        Assert.assertTrue(putMethodPresent);

        boolean patchMethodPresent = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
            @Override
            public boolean apply(ApiMethodDoc input) {
                return input.getMethod().equals("patch");
            }
        });
        Assert.assertTrue(patchMethodPresent);

        boolean deleteMethodPresent = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
            @Override
            public boolean apply(ApiMethodDoc input) {
                return input.getMethod().equals("delete");
            }
        });
        Assert.assertTrue(deleteMethodPresent);
    }


}
