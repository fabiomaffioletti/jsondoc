package org.jsondoc.core.scanner;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.flow.ApiFlow;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.jsondoc.core.pojo.flow.ApiFlowDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;
import org.jsondoc.core.scanner.builder.JSONDocApiAuthDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiErrorDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiGlobalDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiVersionDocBuilder;
import org.jsondoc.core.scanner.validator.JSONDocApiMethodDocValidator;
import org.jsondoc.core.scanner.validator.JSONDocApiObjectDocValidator;
import org.jsondoc.core.util.JSONDocTemplateBuilder;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJSONDocScanner implements JSONDocScanner {
	
	protected Reflections reflections = null;
	
	protected static Logger log = LoggerFactory.getLogger(JSONDocScanner.class);

	public abstract Set<Class<?>> jsondocControllers();
	public abstract Set<Method> jsondocMethods(Class<?> controller);
	public abstract Set<Class<?>> jsondocObjects(List<String> packages);
	public abstract Set<Class<?>> jsondocFlows();
	public abstract Set<Class<?>> jsondocGlobal();
	public abstract Set<Class<?>> jsondocChangelogs();
	public abstract Set<Class<?>> jsondocMigrations();

	public abstract ApiDoc initApiDoc(Class<?> controller);
	public abstract ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc);

	public abstract ApiMethodDoc initApiMethodDoc(Method method, Map<Class<?>, JSONDocTemplate> jsondocTemplates);
	public abstract ApiMethodDoc mergeApiMethodDoc(Method method, ApiMethodDoc apiMethodDoc);
	
	public abstract ApiObjectDoc initApiObjectDoc(Class<?> clazz);
	public abstract ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc);
	
	protected List<ApiMethodDoc> allApiMethodDocs = new ArrayList<ApiMethodDoc>();
	
	protected Set<Class<?>> jsondocControllers = new LinkedHashSet<Class<?>>();
	protected Set<Method>   jsondocMethods = new LinkedHashSet<Method>();
	protected Set<Class<?>> jsondocObjects = new LinkedHashSet<Class<?>>();
	protected Map<Class<?>, JSONDocTemplate> jsondocTemplates = new HashMap<Class<?>, JSONDocTemplate>();
	protected Set<Class<?>> jsondocFlows = new LinkedHashSet<Class<?>>();
	protected Set<Class<?>> jsondocGlobal = new LinkedHashSet<Class<?>>();
	protected Set<Class<?>> jsondocChangelogs = new LinkedHashSet<Class<?>>();
	protected Set<Class<?>> jsondocMigrations = new LinkedHashSet<Class<?>>();
	
	/**
	 * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiObjectDoc</code> objects
	 * @return An <code>ApiDoc</code> object
	 */
	public JSONDoc getJSONDoc(String version, String basePath, List<String> packages, boolean playgroundEnabled, MethodDisplay displayMethodAs) {
		Set<URL> urls = new HashSet<URL>();
		FilterBuilder filter = new FilterBuilder();
		
		log.debug("Found " + packages.size() + " package(s) to scan...");
		for (String pkg : packages) {
			log.debug("Adding package to JSONDoc recursive scan: " + pkg);
			urls.addAll(ClasspathHelper.forPackage(pkg));
			filter.includePackage(pkg);
		}

		reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(filter).setUrls(urls).addScanners(new MethodAnnotationsScanner()));
		
		JSONDoc jsondocDoc = new JSONDoc(version, basePath);
		jsondocDoc.setPlaygroundEnabled(playgroundEnabled);
		jsondocDoc.setDisplayMethodAs(displayMethodAs);
		
		jsondocControllers = jsondocControllers();
		jsondocObjects = jsondocObjects(packages);
		jsondocFlows = jsondocFlows();
		jsondocGlobal = jsondocGlobal();
		jsondocChangelogs = jsondocChangelogs();
		jsondocMigrations = jsondocMigrations();

		for (Class<?> clazz : jsondocObjects) {
			jsondocTemplates.put(clazz, JSONDocTemplateBuilder.build(clazz, jsondocObjects));
		}
		
		jsondocDoc.setApis(getApiDocsMap(jsondocControllers, displayMethodAs));
		jsondocDoc.setObjects(getApiObjectsMap(jsondocObjects));
		jsondocDoc.setFlows(getApiFlowDocsMap(jsondocFlows, allApiMethodDocs));
		jsondocDoc.setGlobal(getApiGlobalDoc(jsondocGlobal, jsondocChangelogs, jsondocMigrations));
		
		return jsondocDoc;
	}
	
	public ApiGlobalDoc getApiGlobalDoc(Set<Class<?>> global, Set<Class<?>> changelogs, Set<Class<?>> migrations) {
		return JSONDocApiGlobalDocBuilder.build(global, changelogs, migrations);
	}
	
	/**
	 * Gets the API documentation for the set of classes passed as argument
	 */
	public Set<ApiDoc> getApiDocs(Set<Class<?>> classes, MethodDisplay displayMethodAs) {
		Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
		for (Class<?> controller : classes) {
			ApiDoc apiDoc = getApiDoc(controller, displayMethodAs);
			apiDocs.add(apiDoc);
		}
		return apiDocs;
	}
	
	/**
	 * Gets the API documentation for a single class annotated with @Api and for its methods, annotated with @ApiMethod
	 * @param controller
	 * @return
	 */
	private ApiDoc getApiDoc(Class<?> controller, MethodDisplay displayMethodAs) {
		log.debug("Getting JSONDoc for class: " + controller.getName());
		ApiDoc apiDoc = initApiDoc(controller);

		apiDoc.setSupportedversions(JSONDocApiVersionDocBuilder.build(controller));
		apiDoc.setAuth(JSONDocApiAuthDocBuilder.getApiAuthDocForController(controller));
		apiDoc.setMethods(getApiMethodDocs(controller, displayMethodAs));
		
		if(controller.isAnnotationPresent(Api.class)) {
			apiDoc = mergeApiDoc(controller, apiDoc);
		}
		
		return apiDoc;
	}
	
	private Set<ApiMethodDoc> getApiMethodDocs(Class<?> controller, MethodDisplay displayMethodAs) {
		Set<ApiMethodDoc> apiMethodDocs = new TreeSet<ApiMethodDoc>();
		Set<Method> methods = jsondocMethods(controller);
		for (Method method : methods) {
			ApiMethodDoc apiMethodDoc = getApiMethodDoc(method, controller, displayMethodAs);
			apiMethodDocs.add(apiMethodDoc);
		}
		jsondocMethods.addAll(methods);
		allApiMethodDocs.addAll(apiMethodDocs);
		return apiMethodDocs;
	}
	
	private ApiMethodDoc getApiMethodDoc(Method method, Class<?> controller, MethodDisplay displayMethodAs) {
		ApiMethodDoc apiMethodDoc = initApiMethodDoc(method, jsondocTemplates);
		
		apiMethodDoc.setDisplayMethodAs(displayMethodAs);
		apiMethodDoc.setApierrors(JSONDocApiErrorDocBuilder.build(method));
		apiMethodDoc.setSupportedversions(JSONDocApiVersionDocBuilder.build(method));
		apiMethodDoc.setAuth(JSONDocApiAuthDocBuilder.getApiAuthDocForMethod(method));

		apiMethodDoc = mergeApiMethodDoc(method, apiMethodDoc);
		apiMethodDoc = JSONDocApiMethodDocValidator.validateApiMethodDoc(apiMethodDoc, displayMethodAs);
		
		return apiMethodDoc;
	}
	
	/**
	 * Gets the API flow documentation for the set of classes passed as argument
	 */
	public Set<ApiFlowDoc> getApiFlowDocs(Set<Class<?>> classes, List<ApiMethodDoc> apiMethodDocs) {
		Set<ApiFlowDoc> apiFlowDocs = new TreeSet<ApiFlowDoc>();
		for (Class<?> clazz : classes) {
			log.debug("Getting JSONDoc for class: " + clazz.getName());
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if(method.isAnnotationPresent(ApiFlow.class)) {
					ApiFlowDoc apiFlowDoc = getApiFlowDoc(method, apiMethodDocs);
					apiFlowDocs.add(apiFlowDoc);
				}	
			}
		}
		return apiFlowDocs;
	}
	
	private ApiFlowDoc getApiFlowDoc(Method method, List<ApiMethodDoc> apiMethodDocs) {
		ApiFlowDoc apiFlowDoc = ApiFlowDoc.buildFromAnnotation(method.getAnnotation(ApiFlow.class), apiMethodDocs);
		return apiFlowDoc;
	}

	public Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes) {
		Set<ApiObjectDoc> apiObjectDocs = new TreeSet<ApiObjectDoc>();
		
		for (Class<?> clazz : classes) {
			log.debug("Getting JSONDoc for class: " + clazz.getName());
			ApiObjectDoc apiObjectDoc = initApiObjectDoc(clazz);
			
			apiObjectDoc.setSupportedversions(JSONDocApiVersionDocBuilder.build(clazz));
			
			apiObjectDoc = mergeApiObjectDoc(clazz, apiObjectDoc);
			if(apiObjectDoc.isShow()) {
				apiObjectDoc = JSONDocApiObjectDocValidator.validateApiObjectDoc(apiObjectDoc);
				apiObjectDocs.add(apiObjectDoc);
			}
			
			apiObjectDoc.setJsondocTemplate(jsondocTemplates.get(clazz));
		}

		return apiObjectDocs;
	}
	
	public Map<String, Set<ApiDoc>> getApiDocsMap(Set<Class<?>> classes, MethodDisplay displayMethodAs) {
		Map<String, Set<ApiDoc>> apiDocsMap = new TreeMap<String, Set<ApiDoc>>();
		Set<ApiDoc> apiDocSet = getApiDocs(classes, displayMethodAs);
		for (ApiDoc apiDoc : apiDocSet) {
			if(apiDocsMap.containsKey(apiDoc.getGroup())) {
				apiDocsMap.get(apiDoc.getGroup()).add(apiDoc);
			} else {
				Set<ApiDoc> groupedPojoDocs = new TreeSet<ApiDoc>();
				groupedPojoDocs.add(apiDoc);
				apiDocsMap.put(apiDoc.getGroup(), groupedPojoDocs);
			}
		}
		return apiDocsMap;
	}
	
	public Map<String, Set<ApiObjectDoc>> getApiObjectsMap(Set<Class<?>> classes) {
		Map<String, Set<ApiObjectDoc>> objectsMap = new TreeMap<String, Set<ApiObjectDoc>>();
		Set<ApiObjectDoc> apiObjectDocSet = getApiObjectDocs(classes); 
		for (ApiObjectDoc apiObjectDoc : apiObjectDocSet) {
			if(objectsMap.containsKey(apiObjectDoc.getGroup())) {
				objectsMap.get(apiObjectDoc.getGroup()).add(apiObjectDoc);
			} else {
				Set<ApiObjectDoc> groupedPojoDocs = new TreeSet<ApiObjectDoc>();
				groupedPojoDocs.add(apiObjectDoc);
				objectsMap.put(apiObjectDoc.getGroup(), groupedPojoDocs);
			}
		}
		return objectsMap;
	}
	
	public Map<String, Set<ApiFlowDoc>> getApiFlowDocsMap(Set<Class<?>> classes, List<ApiMethodDoc> apiMethodDocs) {
		Map<String, Set<ApiFlowDoc>> apiFlowDocsMap = new TreeMap<String, Set<ApiFlowDoc>>();
		Set<ApiFlowDoc> apiFlowDocSet = getApiFlowDocs(classes, apiMethodDocs);
		for (ApiFlowDoc apiFlowDoc : apiFlowDocSet) {
			if(apiFlowDocsMap.containsKey(apiFlowDoc.getGroup())) {
				apiFlowDocsMap.get(apiFlowDoc.getGroup()).add(apiFlowDoc);
			} else {
				Set<ApiFlowDoc> groupedFlowDocs = new TreeSet<ApiFlowDoc>();
				groupedFlowDocs.add(apiFlowDoc);
				apiFlowDocsMap.put(apiFlowDoc.getGroup(), groupedFlowDocs);
			}
		}
		return apiFlowDocsMap;
	}

	public static String[] enumConstantsToStringArray(Object[] enumConstants) {
		String[] sarr = new String[enumConstants.length];
		for (int i = 0; i < enumConstants.length; i++) {
			sarr[i] = String.valueOf(enumConstants[i]);
		}
		return sarr;
	}
	
}
