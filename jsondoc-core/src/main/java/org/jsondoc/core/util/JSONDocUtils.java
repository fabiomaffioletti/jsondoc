package org.jsondoc.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class JSONDocUtils {
	public static final String UNDEFINED = "undefined";
	public static final String WILDCARD = "wildcard";
	private static Reflections reflections = null;
	
	private static Logger log = Logger.getLogger(JSONDocUtils.class);
	
	/**
	 * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiObjectDoc</code> objects
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
		apiDoc.setApis(getApiDocs(reflections.getTypesAnnotatedWith(Api.class)));
		apiDoc.setObjects(getApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class)));
		return apiDoc;
	}
	
	public static Set<ApiDoc> getApiDocs(Set<Class<?>> classes) {
		Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
		for (Class<?> controller : classes) {
			log.debug("Getting JSONDoc for class: " + controller.getName());
			ApiDoc apiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
			apiDoc.setMethods(getApiMethodDocs(controller));
			apiDocs.add(apiDoc);
		}
		return apiDocs;
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
	
	private static List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
		List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
		Method[] methods = controller.getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(ApiMethod.class)) {
				ApiMethodDoc apiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class));
				
				if(method.isAnnotationPresent(ApiHeaders.class)) {
					apiMethodDoc.setHeaders(ApiHeaderDoc.buildFromAnnotation(method.getAnnotation(ApiHeaders.class)));
				}
				
				apiMethodDoc.setPathparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.PATH));
				
				apiMethodDoc.setQueryparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.QUERY));
				
				apiMethodDoc.setBodyobject(ApiBodyObjectDoc.buildFromAnnotation(method));
				
				if(method.isAnnotationPresent(ApiResponseObject.class)) {
				    apiMethodDoc.setResponse( ApiResponseObjectDoc.buildFromAnnotation(method.getAnnotation(ApiResponseObject.class),method) );
				}
				
				if(method.isAnnotationPresent(ApiErrors.class)) {
					apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
				}
				
				apiMethodDocs.add(apiMethodDoc);
			}
			
		}
		return apiMethodDocs;
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
	
	
	/* ********************************************************** *
	 *  Extensions to use for integrating more annotation types.  *
	 *  The idea is to get ApiDoc beans generated by another      *
	 *  facility that reads other annotations (for ex SpringMVC)  *
	 *  and merge the information retrieved from the JsonDoc      *
	 *  annotations if any.                                       *
	 * ********************************************************** */
	
	
	public static Set<ApiDoc> mergeApiDocs( Set<Class<?>> classes, Map<Class<?>,ApiDoc> apiMap )
	{
	    
        for( Class<?> controller : classes )
        {
            log.debug("Getting JSONDoc for class: " + controller.getName());
            
            ApiDoc apiDoc = apiMap.get( controller );
            if( apiDoc == null )
            {
                apiDoc = new ApiDoc();
                apiMap.put( controller, apiDoc );
            }
                
            merge( apiDoc, controller.getAnnotation(Api.class) );
            apiDoc.setMethods( mergeApiMethodDocs(controller, apiDoc.methodMap) );
            
        }
        
        return new HashSet<ApiDoc>( apiMap.values() );
        
    }
	
	
	private static void merge( ApiDoc apiDoc, Api api )
	{
	    if( api == null || apiDoc == null ) return;
	    
	    if( ! isEmpty(api.name()) )        apiDoc.setName( api.name() );
        if( ! isEmpty(api.description()) ) apiDoc.setDescription( api.description() );
    }

	private static void merge( ApiMethodDoc apiMethodDoc, ApiMethod apiMethod  )
	{
	    if( apiMethod == null || apiMethodDoc == null ) return;
	    
	    if( ! isEmpty(apiMethod.path()) )        apiMethodDoc.setPath(apiMethod.path());
	    if( ! isEmpty(apiMethod.consumes()) )    apiMethodDoc.setConsumes(Arrays.asList(apiMethod.consumes()));
	    if( ! isEmpty(apiMethod.produces()) )    apiMethodDoc.setProduces(Arrays.asList(apiMethod.produces()));
	    if( ! isEmpty(apiMethod.description()) ) apiMethodDoc.setDescription(apiMethod.description());
	    
	    if( apiMethodDoc.getVerb() == null )     apiMethodDoc.setVerb( apiMethod.verb() );
	    
	}
	
	public static void merge( ApiParamDoc apiParamDoc, ApiParam apiParam, String type, ApiParamType paramType )
	{
        if( ! apiParam.paramType().equals(paramType) ) return;
        
        if( ! isEmpty(type) ) apiParamDoc.setType( type );
        if( ! isEmpty(apiParam.name()) ) apiParamDoc.setName( apiParam.name() );
        if( ! isEmpty(apiParam.description()) ) apiParamDoc.setDescription( apiParam.description() );
        if( ! isEmpty(apiParam.description()) ) apiParamDoc.setDescription( apiParam.description() );
        
        if( isEmpty(apiParamDoc.getRequired()) ) apiParamDoc.setRequired( String.valueOf(apiParam.required()) );
        
        apiParamDoc.setFormat( apiParam.format() );
        apiParamDoc.setAllowedvalues( apiParam.allowedvalues() );
        
    }
	
	
	private static List<ApiMethodDoc> mergeApiMethodDocs( Class<?> controller, Map<Method,ApiMethodDoc> apiMethodMap )
	{
        
        final Method[] methods = controller.getMethods();
        for( Method method : methods )
        {
            
            ApiMethodDoc apiMethodDoc = apiMethodMap.get( method );
            
            if( method.isAnnotationPresent(ApiMethod.class) )
            {
                if( apiMethodDoc == null )
                {
                    apiMethodDoc = new ApiMethodDoc();
                    apiMethodMap.put( method, apiMethodDoc );
                }
            
                merge( apiMethodDoc, method.getAnnotation(ApiMethod.class) );
            }
            
            /* If there aren't neither SpringMVC nor JsonDoc annotations skips the rest. */
            if( apiMethodDoc == null ) continue;
                
            if( method.isAnnotationPresent(ApiHeaders.class) )
                apiMethodDoc.setHeaders( merge(apiMethodDoc.headerMap, ApiHeaderDoc.buildFromAnnotation(method.getAnnotation(ApiHeaders.class))) );
                
            mergeApiParamDocs( method, apiMethodDoc.pathParam, ApiParamType.PATH );
            apiMethodDoc.setPathparameters( convert(apiMethodDoc.pathParam) );

            mergeApiParamDocs( method, apiMethodDoc.queryParam, ApiParamType.QUERY );
            apiMethodDoc.setQueryparameters( convert(apiMethodDoc.queryParam) );
            
            if( method.isAnnotationPresent(ApiBodyObject.class) )
                apiMethodDoc.setBodyobject( merge(apiMethodDoc.getBodyobject(), ApiBodyObjectDoc.buildFromAnnotation(method)) );
            
            apiMethodDoc.setResponse( ApiResponseObjectDoc.buildFromAnnotation(method) );
                
            if(method.isAnnotationPresent(ApiErrors.class))
                apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
            
        }
        
        return new ArrayList<ApiMethodDoc>( apiMethodMap.values() );
        
    }
	
	
	private static List<ApiHeaderDoc> merge( Map<String,ApiHeaderDoc> headerMap, List<ApiHeaderDoc> headers )
	{
	    
	    if( headerMap == null ) return headers;
	    
	    if( headers != null )
	        for( ApiHeaderDoc header : headers )
	            headerMap.put( header.getName(), header );

	    return new ArrayList<ApiHeaderDoc>( headerMap.values() );
	                
	}

	private static ApiBodyObjectDoc merge( ApiBodyObjectDoc fst, ApiBodyObjectDoc snd )
	{
	    
	    if( fst == null ) return snd;
	    if( snd == null ) return fst;
	    
	    if( snd.getMap() != null ) fst.setMap( snd.getMap() );
	    if( snd.getObject() != null ) fst.setObject( snd.getObject() );
	    if( snd.getMultiple() != null ) fst.setMultiple( snd.getMultiple() );
	    if( snd.getMapKeyObject() != null ) fst.setMapKeyObject( snd.getMapKeyObject() );
	    if( snd.getMapValueObject() != null ) fst.setMapValueObject( snd.getMapValueObject() );
	    
	    return fst;
	    
	}
	
	private static void mergeApiParamDocs( Method method, ApiParamDoc[] apiParamDocs, ApiParamType apiParamType )
	{
	    	    
        final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for( int i = 0; i < parametersAnnotations.length; i++ )
            for (int j = 0; j < parametersAnnotations[i].length; j++)
                if( parametersAnnotations[i][j] instanceof ApiParam )
                {
                    final ApiParam apiParam = (ApiParam) parametersAnnotations[i][j];
                    if( ! apiParam.paramType().equals(apiParamType) ) continue;
                    
                    ApiParamDoc apiParamDoc = apiParamDocs[i];
                    if( apiParamDoc == null )
                    {
                        apiParamDoc = new ApiParamDoc();
                        apiParamDocs[i] = apiParamDoc;
                    }
                    
                    final String paramType = getParamType( method, i );
                    merge( apiParamDoc, apiParam, paramType, apiParamType );
                }

    }
	
	
	public static String getParamType( Method method, Integer index )
	{
	    
        final Class<?> parameter = method.getParameterTypes()[index];
        final Type generic = method.getGenericParameterTypes()[index];
        
        if( Collection.class.isAssignableFrom(parameter) )
        {
            if( generic instanceof ParameterizedType )
            {
                final ParameterizedType parameterizedType = (ParameterizedType) generic;
                final Type type = parameterizedType.getActualTypeArguments()[0];
                if( type instanceof WildcardType )
                    return JSONDocUtils.WILDCARD;

                final Class<?> clazz = (Class<?>) type;
                return getObjectNameFromAnnotatedClass( clazz );
            }
            else
                return JSONDocUtils.UNDEFINED;
            
        }
        else if( method.getReturnType().isArray() )
        {
            final Class<?> classArr = parameter;
            return getObjectNameFromAnnotatedClass( classArr.getComponentType() );
        }
        
        return getObjectNameFromAnnotatedClass( parameter );
        
    }
	
	public static String getReturnType( Method method )
	{
	    
	    final Class<?> returnType = method.getReturnType();
	    final Type generic = method.getGenericReturnType();
	    
	    if( Collection.class.isAssignableFrom(returnType) )
	    {
	        if( generic instanceof ParameterizedType )
	        {
	            final ParameterizedType parameterizedType = (ParameterizedType) generic;
	            final Type type = parameterizedType.getActualTypeArguments()[0];
	            if( type instanceof WildcardType )
	                return JSONDocUtils.WILDCARD;
	            
	            final Class<?> clazz = (Class<?>) type;
	            return getObjectNameFromAnnotatedClass( clazz );
	        }
	        else
	            return JSONDocUtils.UNDEFINED;
	        
	    }
	    else if( method.getReturnType().isArray() )
	    {
	        final Class<?> classArr = returnType;
	        return getObjectNameFromAnnotatedClass( classArr.getComponentType() );
	    }
	    
	    return getObjectNameFromAnnotatedClass( returnType );
	    
	}
	
	
	private static <E> List<E> convert( E[] array )
	{
	    if( array == null ) return null;
	    
	    final List<E> list = new ArrayList<E>( array.length );
	    for( E e : array )
	        if( e != null )
	            list.add( e );
	            
	    return list;
	            
	}
	
	
	public static boolean isEmpty( String value )
	{
	    return value == null || value.isEmpty();
	}
	
	public static boolean isEmpty( Object[] value )
	{
	    return value == null || value.length == 0;
	}
	
}
