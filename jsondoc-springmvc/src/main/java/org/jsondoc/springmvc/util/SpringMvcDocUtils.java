package org.jsondoc.springmvc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.jsondoc.springmvc.annotation.ApiParams;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;


/**
 * A version of the {@link JSONDocUtils} able to build {@link JSONDoc}
 * objects parsing the Spring MVC annotations.
 * 
 * @author mcoluzzi
 */
public class SpringMvcDocUtils
{
	public static final String UNDEFINED = "undefined";
	public static final String WILDCARD = "wildcard";
	private static Reflections reflections = null;
	
	private static Logger log = Logger.getLogger(SpringMvcDocUtils.class);
	
	/**
	 * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiObjectDoc</code> objects
	 * @return An <code>ApiDoc</code> object
	 */
	public static JSONDoc getApiDoc( String version, String basePath, List<String> packages )
	{
	    
		final Set<URL> urls = new HashSet<URL>();
		final FilterBuilder filter = new FilterBuilder();
		
		log.debug("Found " + packages.size() + " package(s) to scan...");
		for( String pkg : packages )
		{
			log.debug("Adding package to JSONDoc recursive scan: " + pkg);
			urls.addAll( ClasspathHelper.forPackage(pkg) );
			filter.includePackage( pkg );
		}

		final ConfigurationBuilder configuration = new ConfigurationBuilder()
        .filterInputsBy( filter )
        .setUrls( urls );
		
		reflections = new Reflections( configuration );
		
		final JSONDoc apiDoc = new JSONDoc( version, basePath );
		apiDoc.setApis( getApiDocs(reflections.getTypesAnnotatedWith(Controller.class)) );
		apiDoc.setObjects(getApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class)));
		
		return apiDoc;
	}
	
	public static Set<ApiDoc> getApiDocs( Set<Class<?>> classes )
	{
	    
		final Map<Class<?>,ApiDoc> apiMap = new HashMap<Class<?>,ApiDoc>();
		for( Class<?> controller : classes )
		{
			log.debug( "Getting SpringMvcDoc for class: " + controller.getName() );
			
			ApiDoc apiDoc = buildApiFromAnnotation( controller );
			apiDoc.methodMap = getApiMethodMap( controller,apiDoc );
			
			apiMap.put( controller, apiDoc );
		}
		
		return JSONDocUtils.mergeApiDocs( classes, apiMap );
		
	}
	
	public static Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes) {
		Set<ApiObjectDoc> pojoDocs = new TreeSet<ApiObjectDoc>();
		for (Class<?> pojo : classes) {
			log.debug("Getting JSONDoc for class: " + pojo.getName());
			ApiObject annotation = pojo.getAnnotation(ApiObject.class);
			ApiObjectDoc pojoDoc = ApiObjectDoc.buildFromAnnotation(annotation, pojo);
			if(annotation.show()) {
				pojoDocs.add(pojoDoc);
			}
		}
		return pojoDocs;
	}
	
	private static Map<Method,ApiMethodDoc> getApiMethodMap( Class<?> controller, ApiDoc apiDoc )
	{
	    
	    final Method[] methods = controller.getMethods();
	    final Map<Method,ApiMethodDoc> apiMethodMap = new HashMap<Method,ApiMethodDoc>( methods.length );
		for( Method method : methods )
		{
		    
			if( ! method.isAnnotationPresent(RequestMapping.class) ) continue;
			
			final ApiMethodDoc apiMethodDoc = buildMethodFromAnnotation( apiDoc, method.getAnnotation(RequestMapping.class) );
			
			final Map<String,ApiParam> paramMap = buildApiParamMap( method );
			apiMethodDoc.pathParam = buildApiPathParamDocs( method, paramMap );
			apiMethodDoc.queryParam = buildApiQueryParamDocs( method, paramMap );
			
			apiMethodDoc.headerMap = buildApiHeaderMap( method );
			apiMethodDoc.setBodyobject( buildApiBodyObject(method) );
			
			apiMethodMap.put( method, apiMethodDoc );
			
		}
		
		return apiMethodMap;
		
	}
	
	public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
		if(annotatedClass.isAnnotationPresent(ApiObject.class)) {
			return annotatedClass.getAnnotation(ApiObject.class).name();
		}
		return clazz.getSimpleName().toLowerCase();
	}
	
	public static boolean isMultiple(Method method) {
		if(Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray()) {
			return true;
		}
		return false;
	}
	
	public static boolean isMultiple(Class<?> clazz) {
		if(Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
			return true;
		}
		return false;
	}
	
	
	private static void buildApiPath( Class<?> controller, StringBuilder sb )
	{
	    
	    final Class<?> superClass = controller.getSuperclass();
	    if( superClass != null )
	        buildApiPath( superClass, sb );
	    
	    final RequestMapping mapping = controller.getAnnotation( RequestMapping.class );
	    if( mapping != null && ! JSONDocUtils.isEmpty(mapping.value()) )
	    {
	      final String path = mapping.value()[0];
	      if( JSONDocUtils.isEmpty(path) ) return;
	      
	      if( sb.lastIndexOf("/") == sb.length() - 1 )
	      {
	          if( path.startsWith( "/" ) )
	              sb.append( path.substring( 1 ) );
	          else
	              sb.append( path );
	      }   
	      else
	      {
	          if( ! path.startsWith( "/" ) )
	              sb.append( "/" );
	          
	          sb.append( path );
	      }
	    }
	    
    }

	private static ApiDoc buildApiFromAnnotation( Class<?> controller )
	{
	    ApiDoc apiDoc = new ApiDoc();
	    
	    final StringBuilder sb = new StringBuilder( 50 );
	    buildApiPath( controller, sb );
	    
        apiDoc.setName( sb.toString() );
	    
	    return apiDoc;
	}
	
	private static ApiMethodDoc buildMethodFromAnnotation( ApiDoc apiDoc, RequestMapping requestMapping )
	{
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        
        String path = apiDoc.getName() != null ? apiDoc.getName() : "";
        if( ! JSONDocUtils.isEmpty(requestMapping.value()) )
        {
            String methodPath = requestMapping.value()[0];            
            path += methodPath.startsWith( "/" ) ? methodPath : "/" + methodPath;
        }
            
        apiMethodDoc.setPath( path );
        apiMethodDoc.setVerb( convert( requestMapping.method() ));
        apiMethodDoc.setConsumes(Arrays.asList(requestMapping.consumes()) );
        apiMethodDoc.setProduces(Arrays.asList(requestMapping.produces()) );
        apiMethodDoc.setHeaders( convertHeaders(requestMapping.headers()) );
        
        return apiMethodDoc;
        
    }
	
	public static ApiParamDoc[] buildApiPathParamDocs( Method method, Map<String,ApiParam> apiParamMap )
	{
	    
	    final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
	    final ApiParamDoc[] docs = new ApiParamDoc[parametersAnnotations.length];
	    
        for( int i = 0; i < parametersAnnotations.length; i++ )
            for( int j = 0; j < parametersAnnotations[i].length; j++ )
                if( parametersAnnotations[i][j] instanceof PathVariable )
                {
                    final PathVariable ann = (PathVariable) parametersAnnotations[i][j];
                    final String paramType = JSONDocUtils.getParamType( method, i );
                    final ApiParam apiParam = apiParamMap.get( ann.value() );
                    
                    final ApiParamDoc apiParamDoc;
                    if( apiParam != null )
                        apiParamDoc = new ApiParamDoc( ann.value(), apiParam.description(), paramType,
                                                       String.valueOf(apiParam.required()), apiParam.allowedvalues(),
                                                       apiParam.format() );
                    else
                        apiParamDoc = new ApiParamDoc( ann.value(), "", paramType, "true", new String[0], "");
                    
                    docs[i] = apiParamDoc;
                }

        return docs;
    }
	
	public static ApiParamDoc[] buildApiQueryParamDocs( Method method, Map<String,ApiParam> apiParamMap )
	{
	    
	    final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
	    final ApiParamDoc[] docs = new ApiParamDoc[parametersAnnotations.length];
	    
	    for( int i = 0; i < parametersAnnotations.length; i++ )
	        for( int j = 0; j < parametersAnnotations[i].length; j++ )
	            if( parametersAnnotations[i][j] instanceof RequestParam )
	            {
	                final RequestParam ann = (RequestParam) parametersAnnotations[i][j];
	                final String paramType = JSONDocUtils.getParamType( method, i );
	                final ApiParam apiParam = apiParamMap.get( ann.value() );
                    
                    final ApiParamDoc apiParamDoc;
                    if( apiParam != null )
                        apiParamDoc = new ApiParamDoc( ann.value(), apiParam.description(), paramType,
                                                       String.valueOf(ann.required()), apiParam.allowedvalues(),
                                                       apiParam.format() );
                    else
                        apiParamDoc = new ApiParamDoc( ann.value(), "", paramType, String.valueOf(ann.required()), new String[0], "");
	                
	                docs[i] = apiParamDoc;
	            }
	    
	    return docs;
	}
	
	public static ApiBodyObjectDoc buildApiBodyObject( Method method )
	{
        boolean multiple = false;
        final int index = getApiBodyObjectIndex(method);
        if( index != -1 )
        {
            final Class<?> parameter = method.getParameterTypes()[index];
            multiple = JSONDocUtils.isMultiple( parameter );
            
            final String[] bodyObj = ApiBodyObjectDoc.getBodyObject( method, index );
            return new ApiBodyObjectDoc(bodyObj[0], bodyObj[1], bodyObj[2], String.valueOf(multiple), bodyObj[3]);
        }
        
        return null;
        
    }
	
	public static Map<String,ApiHeaderDoc> buildApiHeaderMap( Method method )
	{
	    
	    final Map<String,ApiHeaderDoc> headerMap = new HashMap<String,ApiHeaderDoc>();
	    final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
	    for( int i = 0; i < parametersAnnotations.length; i++ )
            for( int j = 0; j < parametersAnnotations[i].length; j++ )
                if( parametersAnnotations[i][j] instanceof RequestHeader )
                {
                    final RequestHeader header = (RequestHeader) parametersAnnotations[i][j];
                    final String description = header.defaultValue() != ValueConstants.DEFAULT_NONE
                                             ? header.value() + ", default: " + header.defaultValue()
                                             : header.value();
                    headerMap.put( header.value(), new ApiHeaderDoc(header.value(), description) );
                }

	    return headerMap;
        
    }
	
	private static int getApiBodyObjectIndex(Method method) {
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof RequestBody) {
                    return i;
                }
            }
        }
        return -1;
    }
	
	
	private static ApiVerb convert( RequestMethod[] methods )
	{
	    if( methods == null || methods.length == 0 )
	        return ApiVerb.GET;
	    
	    final ApiVerb[] verbs = new ApiVerb[methods.length];
	    for( int i = 0; i < methods.length; ++i )
	        switch( methods[i] )
	        {
	        case GET: verbs[i]     = ApiVerb.GET;     break;
	        case PUT: verbs[i]     = ApiVerb.PUT;     break;
	        case HEAD: verbs[i]    = ApiVerb.HEAD;    break;
	        case POST: verbs[i]    = ApiVerb.POST;    break;
	        case TRACE: verbs[i]   = ApiVerb.TRACE;   break;
	        case DELETE: verbs[i]  = ApiVerb.DELETE;  break;
	        case OPTIONS: verbs[i] = ApiVerb.OPTIONS; break;
	        }
	    
	    return verbs[0];
	    
	}
	
	private static List<ApiHeaderDoc> convertHeaders( String[] headers )
	{
	    
	    if( headers == null ) return null;
	    
	    final ApiHeaderDoc[] hearerDocs = new ApiHeaderDoc[headers.length];
	    for( int i = 0; i < headers.length; ++i )
	        hearerDocs[i] = new ApiHeaderDoc( headers[i], "" );
	    
	    return Arrays.asList( hearerDocs );
	    
	}
	
	
	private static Map<String,ApiParam> buildApiParamMap( Method method )
	{
	    
	    final ApiParams apiParams = method.getAnnotation( ApiParams.class );
	    if( apiParams == null ) return Collections.emptyMap();
	    
	    final Map<String,ApiParam> paramMap = new HashMap<String,ApiParam>( apiParams.value().length );
	    for( ApiParam apiParam : apiParams.value() )
	        paramMap.put( apiParam.name(), apiParam );
	            
	    return paramMap;
	}
	
}
