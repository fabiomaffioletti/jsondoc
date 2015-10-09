package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

public class JaxRsQueryParamBuilder {

    public static Set<ApiParamDoc> buildQueryParams(Method method) {
        Set<ApiParamDoc> apiParamDocs = new LinkedHashSet<ApiParamDoc>();

        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            QueryParam queryVar = null;
            ApiQueryParam apiQueryParam = null;
            ApiParamDoc apiParamDoc = null;

            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof QueryParam) {
                    queryVar = (QueryParam) parametersAnnotations[i][j];
                }
                if (parametersAnnotations[i][j] instanceof ApiQueryParam) {
                    apiQueryParam = (ApiQueryParam) parametersAnnotations[i][j];
                }

                if (queryVar != null) {
                    apiParamDoc = new ApiParamDoc(queryVar.value(), "", JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i]), "false", new String[]{}, null, "");
                    mergeApiQueryParamDoc(apiQueryParam, apiParamDoc);
                }
            }

            if (apiParamDoc != null) {
                apiParamDocs.add(apiParamDoc);
            }
        }

        return apiParamDocs;
    }

    private static void mergeApiQueryParamDoc(ApiQueryParam apiQueryParam, ApiParamDoc apiParamDoc) {
        if (apiQueryParam != null) {
            if (apiParamDoc.getName().trim().isEmpty()) {
                apiParamDoc.setName(apiQueryParam.name());
            }
            apiParamDoc.setDescription(apiQueryParam.description());
            apiParamDoc.setAllowedvalues(apiQueryParam.allowedvalues());
            apiParamDoc.setFormat(apiQueryParam.format());
            apiParamDoc.setRequired(apiParamDoc.getRequired());
        }
    }
}
