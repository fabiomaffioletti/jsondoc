package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

public class SpringApiMethodDoc {
    private SpringApiMethodDoc() {
    }

    public static ApiMethodDoc buildFromSpringAnnotation(ApiVersionDoc versionDoc, Method method) {
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        SpringApiMethod springMethod = method.getAnnotation(SpringApiMethod.class);
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setPath(mapping.value()[0]);
        apiMethodDoc.setSupportedversions(getVersionInfo(method, versionDoc));
        apiMethodDoc.setDescription(springMethod.description());
        apiMethodDoc.setVerb(ApiVerb.valueOf(mapping.method()[0].name()));
        apiMethodDoc.setConsumes(Arrays.asList(mapping.consumes()));
        apiMethodDoc.setProduces(Arrays.asList(mapping.produces()));
        return apiMethodDoc;
    }

    private static ApiVersionDoc getVersionInfo(Method method, ApiVersionDoc versionDoc) {
        if (!method.isAnnotationPresent(ApiVersion.class)) {
            return versionDoc;
        }
        ApiVersion annotation = method.getAnnotation(ApiVersion.class);
        ApiVersionDoc apiVersionDoc = new ApiVersionDoc();
        apiVersionDoc.setSince(annotation.since());
        apiVersionDoc.setUntil(annotation.until());
        return apiVersionDoc;
    }
}
