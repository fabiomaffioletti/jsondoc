package org.jsondoc.springmvc;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.util.ApiDocScanner;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.jsondoc.springmvc.pojo.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SpringApiDocScanner implements ApiDocScanner {

    private static Logger log = Logger.getLogger(SpringApiDocScanner.class);

    @Override
    public Set<ApiDoc> scan(Set<Class<?>> classes) {
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
        for (Class<?> controller : classes) {
            log.debug("Getting JSONDoc for class: " + controller.getName());
            ApiDoc apiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
            if (controller.isAnnotationPresent(ApiVersion.class)) {
                apiDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(controller.getAnnotation(ApiVersion.class)));
            }

            //todo : auths
            apiDoc.setMethods(getApiMethodDocs(controller));
            apiDocs.add(apiDoc);
        }
        return apiDocs;
    }

    private static List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
        List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
        ApiVersionDoc apiVersionDoc = new ApiVersionDoc();
        if (controller.isAnnotationPresent(ApiVersion.class)) {
            ApiVersion annotation = controller.getAnnotation(ApiVersion.class);
            apiVersionDoc.setSince(annotation.since());
            apiVersionDoc.setUntil(annotation.until());
        }

        Method[] methods = controller.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SpringApiMethod.class) && method.isAnnotationPresent(RequestMapping.class)) {
                ApiMethodDoc apiMethodDoc = SpringApiMethodDoc.buildFromSpringAnnotation(apiVersionDoc, method);

                apiMethodDoc.setHeaders(SpringApiHeaderDoc.buildFromAnnotation(method));

                apiMethodDoc.setPathparameters(SpringApiParamDoc.getPathVariables(method));

                apiMethodDoc.setQueryparameters(SpringApiParamDoc.getRequestParams(method));

                apiMethodDoc.setBodyobject(SpringApiBodyObjectDoc.buildFromAnnotation(method));

                apiMethodDoc.setResponse(SpringApiResponseObjectDoc.build(method));

                // todo: method level auth?
                apiMethodDocs.add(apiMethodDoc);
            }

        }

        return apiMethodDocs;
    }
}

