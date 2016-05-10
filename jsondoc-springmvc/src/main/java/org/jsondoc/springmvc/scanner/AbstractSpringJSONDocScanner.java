package org.jsondoc.springmvc.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.flow.ApiFlowSet;
import org.jsondoc.core.annotation.global.ApiChangelogSet;
import org.jsondoc.core.annotation.global.ApiGlobal;
import org.jsondoc.core.annotation.global.ApiMigrationSet;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.jsondoc.core.scanner.AbstractJSONDocScanner;
import org.jsondoc.core.scanner.builder.JSONDocApiDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiMethodDocBuilder;
import org.jsondoc.core.scanner.builder.JSONDocApiObjectDocBuilder;
import org.jsondoc.core.util.JSONDocUtils;
import org.jsondoc.springmvc.scanner.builder.SpringConsumesBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringHeaderBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringObjectBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringPathBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringPathVariableBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringProducesBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringQueryParamBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringRequestBodyBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringResponseBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringResponseStatusBuilder;
import org.jsondoc.springmvc.scanner.builder.SpringVerbBuilder;
import org.reflections.Reflections;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Sets;

public abstract class AbstractSpringJSONDocScanner extends AbstractJSONDocScanner {

	@Override
	public Set<Method> jsondocMethods(Class<?> controller) {
		Set<Method> annotatedMethods = new LinkedHashSet<Method>();
		for (Method method : controller.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}
	
	/**
	 * Returns a set of classes that are either return types or body objects
	 * @param candidates
	 * @param clazz
	 * @param type
	 * @param reflections 
	 * @return
	 */
	public static Set<Class<?>> buildJSONDocObjectsCandidates(Set<Class<?>> candidates, Class<?> clazz, Type type, Reflections reflections) {

		if (Map.class.isAssignableFrom(clazz)) {

			if (type instanceof ParameterizedType) {
				Type mapKeyType = ((ParameterizedType) type).getActualTypeArguments()[0];
				Type mapValueType = ((ParameterizedType) type).getActualTypeArguments()[1];

				if (mapKeyType instanceof Class) {
					candidates.add((Class<?>) mapKeyType);
				} else if (mapKeyType instanceof WildcardType) {
					candidates.add(Void.class);
				} else {
					if (mapKeyType instanceof ParameterizedType) {
						candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) mapKeyType).getRawType(), mapKeyType, reflections));
					}
				}

				if (mapValueType instanceof Class) {
					candidates.add((Class<?>) mapValueType);
				} else if (mapValueType instanceof WildcardType) {
					candidates.add(Void.class);
				} else {
					if (mapValueType instanceof ParameterizedType) {
						candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) mapValueType).getRawType(), mapValueType, reflections));
					}
				}

			}

		} else if (Collection.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				Type parametrizedType = ((ParameterizedType) type).getActualTypeArguments()[0];
				candidates.add(clazz);
				
				if (parametrizedType instanceof Class) {
					candidates.add((Class<?>) parametrizedType);
				} else if (parametrizedType instanceof WildcardType) {
					candidates.add(Void.class);
				} else {
					candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType, reflections));
				}
			} else if (type instanceof GenericArrayType) {
				candidates.addAll(buildJSONDocObjectsCandidates(candidates, clazz, ((GenericArrayType) type).getGenericComponentType(), reflections));
			} else {
				candidates.add(clazz);
			}

		} else if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();
			candidates.addAll(buildJSONDocObjectsCandidates(candidates, componentType, type, reflections));

		} else {
			if (type instanceof ParameterizedType) {
				Type parametrizedType = ((ParameterizedType) type).getActualTypeArguments()[0];
				
				if (parametrizedType instanceof Class) {
					Class<?> candidate = (Class<?>) parametrizedType;
					if(candidate.isInterface()) {
						for (Class<?> implementation : reflections.getSubTypesOf(candidate)) {
							buildJSONDocObjectsCandidates(candidates, implementation, parametrizedType, reflections);
						}
					} else {
						candidates.add(candidate);
						candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) type).getRawType(), parametrizedType, reflections));
					}
				} else if (parametrizedType instanceof WildcardType) {
					candidates.add(Void.class);
					candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) type).getRawType(), parametrizedType, reflections));
				} else if(parametrizedType instanceof TypeVariable<?>) {
					candidates.add(Void.class);
					candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) type).getRawType(), parametrizedType, reflections));
				} else {
					candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType, reflections));
				}
			} else if(clazz.isInterface()) {
				for (Class<?> implementation : reflections.getSubTypesOf(clazz)) {
					candidates.addAll(buildJSONDocObjectsCandidates(candidates, implementation, type, reflections));
				}
				
			} else {
				candidates.add(clazz);
			}
		}

		return candidates;
	}

	private void appendSubCandidates(Class<?> clazz, Set<Class<?>> subCandidates, Reflections reflections) {
		if(clazz.isPrimitive() || clazz.equals(Class.class)) {
			return;
		}

		for (Field field : clazz.getDeclaredFields()) {
			Class<?> fieldClass = field.getType();
			Set<Class<?>> fieldCandidates = new HashSet<Class<?>>();
			buildJSONDocObjectsCandidates(fieldCandidates, fieldClass, field.getGenericType(), reflections);

			for(Class<?> candidate: fieldCandidates) {
				if(!subCandidates.contains(candidate)) {
					subCandidates.add(candidate);


					appendSubCandidates(candidate, subCandidates, reflections);
				}
			}
		}
	}


	@Override
	public Set<Class<?>> jsondocObjects(List<String> packages) {
		Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(RequestMapping.class);
		Set<Class<?>> candidates = Sets.newHashSet();
		Set<Class<?>> subCandidates = Sets.newHashSet();
		Set<Class<?>> elected = Sets.newHashSet();
		
		for (Method method : methodsAnnotatedWith) {
			buildJSONDocObjectsCandidates(candidates, method.getReturnType(), method.getGenericReturnType(), reflections);
			Integer requestBodyParameterIndex = JSONDocUtils.getIndexOfParameterWithAnnotation(method, RequestBody.class);
			if(requestBodyParameterIndex != -1) {
				candidates.addAll(buildJSONDocObjectsCandidates(candidates, method.getParameterTypes()[requestBodyParameterIndex], method.getGenericParameterTypes()[requestBodyParameterIndex], reflections));
			}
		}
		
		// This is to get objects' fields that are not returned nor part of the body request of a method, but that are a field
		// of an object returned or a body  of a request of a method
		for (Class<?> clazz : candidates) {
			appendSubCandidates(clazz, subCandidates, reflections);
		}

		candidates.addAll(subCandidates);
		
		for (Class<?> clazz : candidates) {
			if(clazz.getPackage() != null) {
				for (String pkg : packages) {
					if(clazz.getPackage().getName().contains(pkg)) {
						elected.add(clazz);
					}
				}
			}
		}
		
		return elected;
	}
	
	@Override
	public Set<Class<?>> jsondocFlows() {
		return reflections.getTypesAnnotatedWith(ApiFlowSet.class, true);
	}

	/**
	 * ApiDoc is initialized with the Controller's simple class name.
	 */
	@Override
	public ApiDoc initApiDoc(Class<?> controller) {
		ApiDoc apiDoc = new ApiDoc();
		apiDoc.setName(controller.getSimpleName());
		apiDoc.setDescription(controller.getSimpleName());
		return apiDoc;
	}

	/**
	 * Once the ApiDoc has been initialized and filled with other data (version,
	 * auth, etc) it's time to merge the documentation with JSONDoc annotation,
	 * if existing.
	 */
	@Override
	public ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc) {
		ApiDoc jsondocApiDoc = JSONDocApiDocBuilder.build(controller);
		BeanUtils.copyProperties(jsondocApiDoc, apiDoc, new String[] { "methods", "supportedversions", "auth" });
		return apiDoc;
	}

	@Override
	public ApiMethodDoc initApiMethodDoc(Method method, Map<Class<?>, JSONDocTemplate> jsondocTemplates) {
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setPath(SpringPathBuilder.buildPath(method));
		apiMethodDoc.setMethod(method.getName());
		apiMethodDoc.setVerb(SpringVerbBuilder.buildVerb(method));
		apiMethodDoc.setProduces(SpringProducesBuilder.buildProduces(method));
		apiMethodDoc.setConsumes(SpringConsumesBuilder.buildConsumes(method));
		apiMethodDoc.setHeaders(SpringHeaderBuilder.buildHeaders(method));
		apiMethodDoc.setPathparameters(SpringPathVariableBuilder.buildPathVariable(method));
		apiMethodDoc.setQueryparameters(SpringQueryParamBuilder.buildQueryParams(method));
		apiMethodDoc.setBodyobject(SpringRequestBodyBuilder.buildRequestBody(method));
		apiMethodDoc.setResponse(SpringResponseBuilder.buildResponse(method));
		apiMethodDoc.setResponsestatuscode(SpringResponseStatusBuilder.buildResponseStatusCode(method));
		
		Integer index = JSONDocUtils.getIndexOfParameterWithAnnotation(method, RequestBody.class);
		if (index != -1) {
			apiMethodDoc.getBodyobject().setJsondocTemplate(jsondocTemplates.get(method.getParameterTypes()[index]));
		}

		return apiMethodDoc;
	}

	@Override
	public ApiMethodDoc mergeApiMethodDoc(Method method, ApiMethodDoc apiMethodDoc) {
		if (method.isAnnotationPresent(ApiMethod.class) && method.getDeclaringClass().isAnnotationPresent(Api.class)) {
			ApiMethodDoc jsondocApiMethodDoc = JSONDocApiMethodDocBuilder.build(method);
			BeanUtils.copyProperties(jsondocApiMethodDoc, apiMethodDoc, new String[] { "path", "verb", "produces", "consumes", "headers", "pathparameters", "queryparameters", "bodyobject", "response", "responsestatuscode", "apierrors", "supportedversions", "auth", "displayMethodAs" });
		}
		return apiMethodDoc;
	}

	@Override
	public ApiObjectDoc initApiObjectDoc(Class<?> clazz) {
		return SpringObjectBuilder.buildObject(clazz);
	}

	@Override
	public ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc) {
		if(clazz.isAnnotationPresent(ApiObject.class)) {
			ApiObjectDoc jsondocApiObjectDoc = JSONDocApiObjectDocBuilder.build(clazz);
			BeanUtils.copyProperties(jsondocApiObjectDoc, apiObjectDoc);
		}
		return apiObjectDoc;
	}

	@Override
	public Set<Class<?>> jsondocGlobal() {
		return reflections.getTypesAnnotatedWith(ApiGlobal.class, true);
	}
	
	@Override
	public Set<Class<?>> jsondocChangelogs() {
		return reflections.getTypesAnnotatedWith(ApiChangelogSet.class, true);
	}

	@Override
	public Set<Class<?>> jsondocMigrations() {
		return reflections.getTypesAnnotatedWith(ApiMigrationSet.class, true);
	}

}
