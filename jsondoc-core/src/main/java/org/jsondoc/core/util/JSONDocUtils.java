package org.jsondoc.core.util;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.net.URL;
import java.util.*;

public class JSONDocUtils {
    public static final String UNDEFINED = "undefined";
    public static final String ANONYMOUS = "anonymous";

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

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(filter)
                .setUrls(urls)
        );

        JSONDoc apiDoc = new JSONDoc(version, basePath);
        apiDoc.setApis(getApiDocsMap(reflections.getTypesAnnotatedWith(Api.class)));
        apiDoc.setObjects(getApiObjectsMap(reflections.getTypesAnnotatedWith(ApiObject.class)));

        return apiDoc;
    }

    public static Set<ApiDoc> getApiDocs(Set<Class<?>> classes) {
        Set<Class<? extends ApiDocScanner>> scanners = new Reflections("org.jsondoc").getSubTypesOf(ApiDocScanner.class);
        System.out.println("FINDERS = " + scanners.size());
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();

        for (Class<? extends ApiDocScanner> scanner : scanners) {
            try {
                ApiDocScanner apiDocScanner = scanner.newInstance();

                Set<ApiDoc> scan = apiDocScanner.scan(classes);

                apiDocs.addAll(scan);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("failed to create ApiDocFinder of class " + ApiDocScanner.class, e);
            }
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

    public static String[] enumConstantsToStringArray(Object[] enumConstants) {
        String[] sarr = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            sarr[i] = String.valueOf(enumConstants[i]);
        }
        return sarr;
    }

}
