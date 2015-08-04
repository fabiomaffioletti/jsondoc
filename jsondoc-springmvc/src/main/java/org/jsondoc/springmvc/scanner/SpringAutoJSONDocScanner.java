package org.jsondoc.springmvc.scanner;

import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.*;
import org.jsondoc.springmvc.util.NoAnnotationDocBuilder;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class SpringAutoJSONDocScanner extends SpringJSONDocScanner {

    private final Map<String, Set<ApiObjectDoc>> noAnnotationObjectDocSet = new TreeMap<String, Set<ApiObjectDoc>>();

    @Override
    public JSONDoc getJSONDoc(String version, String basePath, List<String> packages, boolean playgroundEnabled, JSONDoc.MethodDisplay displayMethodAs) {
        HashSet<URL> urls = new HashSet<URL>();
        FilterBuilder filter = new FilterBuilder();
        log.debug("Found " + packages.size() + " package(s) to scan...");

        for (String pkg : packages) {
            log.debug("Adding package to JSONDoc recursive scan: " + pkg);
            urls.addAll(ClasspathHelper.forPackage(pkg));
            filter.includePackage(pkg);
        }

        this.reflections = new Reflections((new ConfigurationBuilder()).filterInputsBy(filter).setUrls(urls));
        JSONDoc jsondocDoc1 = new JSONDoc(version, basePath);

        Set<Class<?>> jsonDocAnnotatedClasses = this.reflections.getTypesAnnotatedWith(Api.class, true);
        Set<Class<?>> restControllerClasses = this.reflections.getTypesAnnotatedWith(org.springframework.web.bind.annotation.RestController.class, true);
        Set<Class<?>> controllerClasses = this.reflections.getTypesAnnotatedWith(org.springframework.stereotype.Controller.class, true);
        Set<Class<?>> aggregatedClasses = new HashSet<Class<?>>();
        aggregatedClasses.addAll(jsonDocAnnotatedClasses);
        aggregatedClasses.addAll(restControllerClasses);
        aggregatedClasses.addAll(controllerClasses);
        log.info("Found " + jsonDocAnnotatedClasses.size() + " doc annotated controllers");
        log.info("Found " + restControllerClasses.size() + " RestControllers");
        log.info("Found " + controllerClasses.size() + " Controllers");
        log.debug("The aggregated list contains " + aggregatedClasses + " classes");

        jsondocDoc1.setApis(this.getApiDocsMap(aggregatedClasses, displayMethodAs));

        Map<String, Set<ApiObjectDoc>> objectDocSet = this.getApiObjectsMap(this.reflections.getTypesAnnotatedWith(ApiObject.class, true));
        jsondocDoc1.setObjects(objectDocSet);
        jsondocDoc1.setObjects(noAnnotationObjectDocSet);

        jsondocDoc1.setFlows(this.getApiFlowDocsMap(this.reflections.getTypesAnnotatedWith(ApiFlowSet.class, true), this.allApiMethodDocs));
        jsondocDoc1.setPlaygroundEnabled(playgroundEnabled);
        jsondocDoc1.setDisplayMethodAs(displayMethodAs);
        return jsondocDoc1;
    }

    @Override
    public ApiMethodDoc mergeApiMethodDoc(Method method, Class<?> controller, ApiMethodDoc apiMethodDoc) {
        super.mergeApiMethodDoc(method, controller, apiMethodDoc);

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class[] parameterTypes = method.getParameterTypes();

        int i = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            Class parameterType = parameterTypes[i++];

            ApiParamDoc pathParamDoc = null;
            boolean apiPathParamFound = false;
            ApiParamDoc requestParamDoc = null;
            boolean apiQueryParamFound = false;

            for (Annotation annotation : annotations) {
                if (annotation instanceof PathVariable) {
                    PathVariable pathVariable = (PathVariable) annotation;
                    pathParamDoc = NoAnnotationDocBuilder.buildApiPathParamDoc(parameterType, pathVariable, i);
                } else if (annotation instanceof RequestParam) {
                    RequestParam requestParam = (RequestParam) annotation;
                    requestParamDoc = NoAnnotationDocBuilder.buildApiRequestParamDoc(parameterType, requestParam);
                } else if (annotation instanceof RequestBody) {
                    apiMethodDoc.setBodyobject(NoAnnotationDocBuilder.buildApiRequestBodyDoc(parameterType));
                    addToNoAnnotationObjectsDocMap(parameterType);
                } else if (annotation instanceof ModelAttribute) {
                    apiMethodDoc.setBodyobject(NoAnnotationDocBuilder.buildApiRequestBodyDoc(parameterType));
                    addToNoAnnotationObjectsDocMap(parameterType);
                } else if( annotation instanceof ApiPathParam) {
                    apiPathParamFound = true;
                } else if( annotation instanceof ApiQueryParam) {
                    apiQueryParamFound = true;
                }
            }
            if(pathParamDoc != null && !apiPathParamFound) {
                apiMethodDoc.getPathparameters().add(pathParamDoc);
                if (pathParamDoc.getName().contains(NoAnnotationDocBuilder.UNKNOWN_PARAM_NAME_PREFIX))
                    log.warn("@PathVariable value is missing for documentation in " + controller.getCanonicalName() + "."
                            + method.getName());
            }
            if(requestParamDoc != null && !apiQueryParamFound) {
                apiMethodDoc.getQueryparameters().add(requestParamDoc);
            }
        }

        Annotation[] returnTypeAnnotations = method.getReturnType().getAnnotations();
        boolean apiResponseObjectAnnotationExists = false;
        for(Annotation annotation : returnTypeAnnotations) {
            if(annotation instanceof ApiResponseObject) {
                apiResponseObjectAnnotationExists = true;
                break;
            }
        }
        if(!apiResponseObjectAnnotationExists) {
            apiMethodDoc.setResponse(NoAnnotationDocBuilder.buildApiResponseObjectDoc(method));
            addToNoAnnotationObjectsDocMap(method.getReturnType());
        }


        return apiMethodDoc;
    }

    @Override
    public Set<ApiDoc> getApiDocs(Set<Class<?>> classes, JSONDoc.MethodDisplay displayMethodAs) {
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
        for (Class<?> controller : classes) {
            ApiDoc apiDoc = getApiDoc(controller, displayMethodAs);
            apiDocs.add(apiDoc);
        }
        return apiDocs;
    }

    private ApiDoc getApiDoc(Class<?> controller, JSONDoc.MethodDisplay displayMethodAs) {
        log.debug("Getting JSONDoc for class: " + controller.getName());
        Api apiAnnotation = controller.getAnnotation(Api.class);

        ApiDoc apiDoc;
        if (apiAnnotation != null)
            apiDoc = ApiDoc.buildFromAnnotation(apiAnnotation);
        else
            apiDoc = NoAnnotationDocBuilder.buildApiDoc(controller);

        apiDoc.setSupportedversions(ApiVersionDoc.build(controller));
        apiDoc.setAuth(this.getApiAuthDocForController(controller));
        apiDoc.setMethods(this.getApiMethodDocs(controller, displayMethodAs));
        apiDoc = this.mergeApiDoc(controller, apiDoc);
        return apiDoc;
    }

    private Set<ApiMethodDoc> getApiMethodDocs(Class<?> controller, JSONDoc.MethodDisplay displayMethodAs) {
        Set<ApiMethodDoc> apiMethodDocs = new TreeSet<ApiMethodDoc>();
        Method[] methods = controller.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ApiMethod.class)) {
                apiMethodDocs.add(getApiMethodDoc(method, controller, displayMethodAs));
            } else {
                apiMethodDocs.add(getApiMethodDocNotAnnotated(method, controller));
            }
        }
        allApiMethodDocs.addAll(apiMethodDocs);
        return apiMethodDocs;
    }

    private ApiMethodDoc getApiMethodDocNotAnnotated(Method method, Class<?> controller) {
        ApiMethodDoc apiMethodDoc = NoAnnotationDocBuilder.buildApiMethodDoc(method, controller);
        apiMethodDoc = mergeApiMethodDoc(method, controller, apiMethodDoc);
        return apiMethodDoc;
    }

    private void addToNoAnnotationObjectsDocMap(Class<?> clazz) {
        if(clazz.isPrimitive()
                || clazz.getCanonicalName().startsWith("java.")
                || clazz.getCanonicalName().startsWith("javax."))
            return;

        ApiObjectDoc apiObjectDoc = NoAnnotationDocBuilder.buildApiObjectDoc(clazz);
        if (noAnnotationObjectDocSet.containsKey(apiObjectDoc.getGroup())) {
            noAnnotationObjectDocSet.get(apiObjectDoc.getGroup()).add(apiObjectDoc);
        } else {
            TreeSet<ApiObjectDoc> groupedPojoDocs = new TreeSet<ApiObjectDoc>();
            groupedPojoDocs.add(apiObjectDoc);
            noAnnotationObjectDocSet.put(apiObjectDoc.getGroup(), groupedPojoDocs);
        }
    }

    protected Map<String, Set<ApiObjectDoc>> getNoAnnotationObjectDocSet() {
        return noAnnotationObjectDocSet;
    }
}
