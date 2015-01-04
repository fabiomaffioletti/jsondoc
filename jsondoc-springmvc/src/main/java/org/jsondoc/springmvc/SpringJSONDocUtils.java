package org.jsondoc.springmvc;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.*;
import org.jsondoc.core.util.JSONDocUtils;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.jsondoc.springmvc.pojo.SpringApiMethodDoc;
import org.jsondoc.springmvc.pojo.SpringApiParamDoc;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class SpringJSONDocUtils {

    public static final String UNDEFINED = "undefined";
    public static final String ANONYMOUS = "anonymous";
    private static Reflections reflections = null;

    private static Logger log = Logger.getLogger(JSONDocUtils.class);

    /**
     * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiObjectDoc</code> objects
     *
     * @return An <code>ApiDoc</code> object
     */
    public static JSONDoc getApiDoc(String version, String basePath, List<String> packages) {
        Set<URL> urls = new HashSet<URL>();
        FilterBuilder filter = new FilterBuilder();

        log.debug("Found " + packages.size() + " package(s) to scan...");
        for (String pkg : packages) {
            log.debug("Adding package to JSONDoc recursive scan: " + pkg);
            urls.addAll(ClasspathHelper.forPackage(pkg));
            filter.includePackage(pkg);
        }

        reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(filter)
                .setUrls(urls)
        );

        JSONDoc apiDoc = new JSONDoc(version, basePath);
        apiDoc.setApis(getApiDocsMap(reflections.getTypesAnnotatedWith(Api.class)));
        apiDoc.setObjects(getApiObjectsMap(reflections.getTypesAnnotatedWith(ApiObject.class)));

        return apiDoc;
    }

    public static Set<ApiDoc> getApiDocs(Set<Class<?>> classes) {
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
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

    public static Map<String, Set<ApiDoc>> getApiDocsMap(Set<Class<?>> classes) {
        Map<String, Set<ApiDoc>> apiDocsMap = new TreeMap<String, Set<ApiDoc>>();
        Set<ApiDoc> apiDocSet = getApiDocs(classes);
        for (ApiDoc apiDoc : apiDocSet) {
            if (apiDocsMap.containsKey(apiDoc.getGroup())) {
                apiDocsMap.get(apiDoc.getGroup()).add(apiDoc);
            } else {
                Set<ApiDoc> groupedPojoDocs = new TreeSet<ApiDoc>();
                groupedPojoDocs.add(apiDoc);
                apiDocsMap.put(apiDoc.getGroup(), groupedPojoDocs);
            }
        }
        return apiDocsMap;
    }

    public static Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes) {
        Set<ApiObjectDoc> pojoDocs = new TreeSet<ApiObjectDoc>();
        for (Class<?> pojo : classes) {
            log.debug("Getting JSONDoc for class: " + pojo.getName());
            ApiObject annotation = pojo.getAnnotation(ApiObject.class);
            ApiObjectDoc pojoDoc = ApiObjectDoc.buildFromAnnotation(annotation, pojo);
            if (pojo.isAnnotationPresent(ApiVersion.class)) {
                pojoDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(pojo.getAnnotation(ApiVersion.class)));
            }

            if (annotation.show()) {
                pojoDocs.add(pojoDoc);
            }
        }
        return pojoDocs;
    }

    public static Map<String, Set<ApiObjectDoc>> getApiObjectsMap(Set<Class<?>> classes) {
        Map<String, Set<ApiObjectDoc>> objectsMap = new TreeMap<String, Set<ApiObjectDoc>>();
        Set<ApiObjectDoc> apiObjectDocSet = getApiObjectDocs(classes);
        for (ApiObjectDoc apiObjectDoc : apiObjectDocSet) {
            if (objectsMap.containsKey(apiObjectDoc.getGroup())) {
                objectsMap.get(apiObjectDoc.getGroup()).add(apiObjectDoc);
            } else {
                Set<ApiObjectDoc> groupedPojoDocs = new TreeSet<ApiObjectDoc>();
                groupedPojoDocs.add(apiObjectDoc);
                objectsMap.put(apiObjectDoc.getGroup(), groupedPojoDocs);
            }
        }
        return objectsMap;
    }

    private static List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
        List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
        Method[] methods = controller.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SpringApiMethod.class) && method.isAnnotationPresent(RequestMapping.class)) {
                ApiMethodDoc apiMethodDoc = SpringApiMethodDoc.buildFromSpringAnnotation(
                        method.getAnnotation(SpringApiMethod.class), method.getAnnotation(RequestMapping.class));

//                if (method.isAnnotationPresent(ApiHeaders.class)) {
//                    apiMethodDoc.setHeaders(ApiHeaderDoc.buildFromAnnotation(method.getAnnotation(ApiHeaders.class)));
//                }
//
                apiMethodDoc.setPathparameters(SpringApiParamDoc.getPathVariables(method));
//
//                apiMethodDoc.setQueryparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.QUERY));
//
//                apiMethodDoc.setBodyobject(ApiBodyObjectDoc.buildFromAnnotation(method));
//
//                if (method.isAnnotationPresent(ApiResponseObject.class)) {
//                    apiMethodDoc.setResponse(ApiResponseObjectDoc.buildFromAnnotation(method.getAnnotation(ApiResponseObject.class), method));
//                }
//
//                if (method.isAnnotationPresent(ApiErrors.class)) {
//                    apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
//                }
//
//                if (method.isAnnotationPresent(ApiVersion.class)) {
//                    apiMethodDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(method.getAnnotation(ApiVersion.class)));
//                }
//
//                apiMethodDoc.setAuth(getApiAuthDocForMethod(method, method.getDeclaringClass()));
//
                apiMethodDocs.add(apiMethodDoc);
            }

        }
        return apiMethodDocs;
    }

    public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
        Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
        if (annotatedClass.isAnnotationPresent(ApiObject.class)) {
            return annotatedClass.getAnnotation(ApiObject.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public static boolean isMultiple(Method method) {
        if (Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray()) {
            return true;
        }
        return false;
    }

    public static boolean isMultiple(Class<?> clazz) {
        if (Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
            return true;
        }
        return false;
    }

    public static String[] enumConstantsToStringArray(Object[] enumConstants) {
        String[] sarr = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            sarr[i] = String.valueOf(enumConstants[i]);
        }
        return sarr;
    }

    private static ApiAuthDoc getApiAuthDocForController(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ApiAuthNone.class)) {
            return ApiAuthDoc.buildFromApiAuthNoneAnnotation(clazz.getAnnotation(ApiAuthNone.class));
        }

        if (clazz.isAnnotationPresent(ApiAuthBasic.class)) {
            return ApiAuthDoc.buildFromApiAuthBasicAnnotation(clazz.getAnnotation(ApiAuthBasic.class));
        }

        return null;
    }

    private static ApiAuthDoc getApiAuthDocForMethod(Method method, Class<?> clazz) {
        if (method.isAnnotationPresent(ApiAuthNone.class)) {
            return ApiAuthDoc.buildFromApiAuthNoneAnnotation(method.getAnnotation(ApiAuthNone.class));
        }

        if (method.isAnnotationPresent(ApiAuthBasic.class)) {
            return ApiAuthDoc.buildFromApiAuthBasicAnnotation(method.getAnnotation(ApiAuthBasic.class));
        }

        return getApiAuthDocForController(clazz);
    }

}

