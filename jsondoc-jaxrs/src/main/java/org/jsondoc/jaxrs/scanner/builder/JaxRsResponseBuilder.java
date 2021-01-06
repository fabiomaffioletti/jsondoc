package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

public class JaxRsResponseBuilder {

    public static ApiResponseObjectDoc buildResponse(Method method) {
        ApiResponseObject repo = method.getAnnotation(ApiResponseObject.class);
        if (repo != null) {
            Class<?> clazz = repo.clazz();
            if (clazz != null) {
                ApiResponseObjectDoc apiResponseObjectDoc = new ApiResponseObjectDoc(
                        JSONDocTypeBuilder.build(new JSONDocType(), clazz, null)
                );
                return apiResponseObjectDoc;
            }
        }

        ApiResponseObjectDoc apiResponseObjectDoc = new ApiResponseObjectDoc(
                JSONDocTypeBuilder.build(new JSONDocType(), method.getReturnType(), method.getGenericReturnType())
        );

        if (method.getReturnType().isAssignableFrom(Response.class)) {
            apiResponseObjectDoc.getJsondocType().getType().remove(0);
        }
        return apiResponseObjectDoc;
    }

}
