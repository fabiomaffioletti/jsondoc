package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

public class SpringApiMethodDoc extends ApiMethodDoc {
    public static ApiMethodDoc buildFromSpringAnnotation(SpringApiMethod method, RequestMapping mapping) {
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setPath(mapping.value()[0]);
        apiMethodDoc.setDescription(method.description());
        apiMethodDoc.setVerb(ApiVerb.valueOf(mapping.method()[0].name()));
        apiMethodDoc.setConsumes(Arrays.asList(mapping.consumes()));
        apiMethodDoc.setProduces(Arrays.asList(mapping.produces()));
        return apiMethodDoc;
    }
}
