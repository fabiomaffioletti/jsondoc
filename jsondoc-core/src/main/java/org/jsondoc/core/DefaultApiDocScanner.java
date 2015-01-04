package org.jsondoc.core;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.*;
import org.jsondoc.core.util.ApiDocScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultApiDocScanner implements ApiDocScanner {
    private static Logger log = Logger.getLogger(DefaultApiDocScanner.class);

    @Override
    public Set<ApiDoc> scan(Set<Class<?>> classes) {
        Set<ApiDoc> apiDocs = new LinkedHashSet<ApiDoc>();
        for (Class<?> controller : classes) {
            log.debug("Getting JSONDoc for class: " + controller.getName());
            ApiDoc apiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
            if (controller.isAnnotationPresent(ApiVersion.class)) {
                apiDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(controller.getAnnotation(ApiVersion.class)));
            }

            apiDoc.setAuth(getApiAuthDocForController(controller));
            apiDoc.setMethods(getApiMethodDocs(controller));
            apiDocs.add(apiDoc);
        }
        return apiDocs;
    }

    private List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
        List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
        Method[] methods = controller.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ApiMethod.class)) {
                ApiMethodDoc apiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class));

                if (method.isAnnotationPresent(ApiHeaders.class)) {
                    apiMethodDoc.setHeaders(ApiHeaderDoc.buildFromAnnotation(method.getAnnotation(ApiHeaders.class)));
                }

                apiMethodDoc.setPathparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.PATH));

                apiMethodDoc.setQueryparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.QUERY));

                apiMethodDoc.setBodyobject(ApiBodyObjectDoc.buildFromAnnotation(method));

                if (method.isAnnotationPresent(ApiResponseObject.class)) {
                    apiMethodDoc.setResponse(ApiResponseObjectDoc.buildFromAnnotation(method.getAnnotation(ApiResponseObject.class), method));
                }

                if (method.isAnnotationPresent(ApiErrors.class)) {
                    apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
                }

                if (method.isAnnotationPresent(ApiVersion.class)) {
                    apiMethodDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(method.getAnnotation(ApiVersion.class)));
                }

                apiMethodDoc.setAuth(getApiAuthDocForMethod(method, method.getDeclaringClass()));

                apiMethodDocs.add(apiMethodDoc);
            }

        }
        return apiMethodDocs;
    }

    private ApiAuthDoc getApiAuthDocForMethod(Method method, Class<?> clazz) {
        if (method.isAnnotationPresent(ApiAuthNone.class)) {
            return ApiAuthDoc.buildFromApiAuthNoneAnnotation(method.getAnnotation(ApiAuthNone.class));
        }

        if (method.isAnnotationPresent(ApiAuthBasic.class)) {
            return ApiAuthDoc.buildFromApiAuthBasicAnnotation(method.getAnnotation(ApiAuthBasic.class));
        }

        return getApiAuthDocForController(clazz);
    }

    private ApiAuthDoc getApiAuthDocForController(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ApiAuthNone.class)) {
            return ApiAuthDoc.buildFromApiAuthNoneAnnotation(clazz.getAnnotation(ApiAuthNone.class));
        }

        if (clazz.isAnnotationPresent(ApiAuthBasic.class)) {
            return ApiAuthDoc.buildFromApiAuthBasicAnnotation(clazz.getAnnotation(ApiAuthBasic.class));
        }

        return null;
    }
}
