package org.jsondoc.jaxrs.scanner;

import com.google.common.collect.Sets;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
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
import org.jsondoc.jaxrs.scanner.builder.JaxRsConsumesBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsHeaderBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsObjectBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsPathBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsPathVariableBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsProducesBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsQueryParamBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsRequestBodyBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsResponseBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsResponseStatusBuilder;
import org.jsondoc.jaxrs.scanner.builder.JaxRsVerbBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsJSONDocScanner extends AbstractJSONDocScanner {
    public static final Class[] HTTP_METHODS = {GET.class, PUT.class, POST.class, DELETE.class, HEAD.class};
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsJSONDocScanner.class);

    /**
     * Returns a set of classes that are either return types or body objects
     *
     * @param candidates
     * @param clazz
     * @param type
     * @return
     */
    public static Set<Class<?>> buildJSONDocObjectsCandidates(Set<Class<?>> candidates, Class<?> clazz, Type type) {

        if (Map.class.isAssignableFrom(clazz)) {

            if (type instanceof ParameterizedType) {
                Type mapKeyType = ((ParameterizedType) type).getActualTypeArguments()[0];
                Type mapValueType = ((ParameterizedType) type).getActualTypeArguments()[1];

                if (mapKeyType instanceof Class) {
                    candidates.add((Class<?>) mapKeyType);
                } else if (mapKeyType instanceof WildcardType) {
                    candidates.add(Void.class);
                } else {
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) mapKeyType).getRawType(), mapKeyType));
                }

                if (mapValueType instanceof Class) {
                    candidates.add((Class<?>) mapValueType);
                } else if (mapValueType instanceof WildcardType) {
                    candidates.add(Void.class);
                } else {
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) mapValueType).getRawType(), mapValueType));
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
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType));
                }
            } else if (type instanceof GenericArrayType) {
                candidates.addAll(buildJSONDocObjectsCandidates(candidates, clazz, ((GenericArrayType) type).getGenericComponentType()));
            } else {
                candidates.add(clazz);
            }

        } else if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            candidates.addAll(buildJSONDocObjectsCandidates(candidates, componentType, type));

        } else {
            if (type instanceof ParameterizedType) {
                Type parametrizedType = ((ParameterizedType) type).getActualTypeArguments()[0];

                if (parametrizedType instanceof Class) {
                    candidates.add((Class<?>) parametrizedType);
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) type).getRawType(), parametrizedType));
                } else if (parametrizedType instanceof WildcardType) {
                    candidates.add(Void.class);
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) type).getRawType(), parametrizedType));
                } else {
                    candidates.addAll(buildJSONDocObjectsCandidates(candidates, (Class<?>) ((ParameterizedType) parametrizedType).getRawType(), parametrizedType));
                }
            } else {
                candidates.add(clazz);
            }
        }

        return candidates;
    }

    /**
     * find all classes that have a {@link Path} annotation
     *
     * @return collection of classes with required annotation
     */
    @Override
    public Set<Class<?>> jsondocControllers() {
        return reflections.getTypesAnnotatedWith(Path.class, true);
    }

    /**
     * From given controller find all methods that have HTTP method annotation
     *
     * @param controller
     * @return
     */
    @Override
    public Set<Method> jsondocMethods(Class<?> controller) {
        Set<Method> annotatedMethods = new LinkedHashSet<Method>();
        for (Method method : controller.getDeclaredMethods()) {
            if (isHttpMethod(method)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private boolean isHttpMethod(Method method) {
        return hasAnyAnnotation(method, HTTP_METHODS);
    }

    /**
     * @param method to check
     * @param clazz  annotations to check for
     * @return true if method has any of the given annotations
     */
    private boolean hasAnyAnnotation(Method method, Class... clazz) {
        for (Class aClass : clazz) {
            if (method.isAnnotationPresent(aClass)) {
                return true;
            }
        }
        return false;
    }

    private Set<Method> getHttpMethods() {
        Set<Method> ret = new HashSet<Method>();

        for (Class m : HTTP_METHODS) {
            ret.addAll(reflections.getMethodsAnnotatedWith(m));
        }
        return ret;
    }

    @Override
    public Set<Class<?>> jsondocObjects(List<String> packages) {
        Set<Method> methodsAnnotatedWith = getHttpMethods();

        Set<Class<?>> candidates = Sets.newHashSet();
        Set<Class<?>> subCandidates = Sets.newHashSet();
        Set<Class<?>> elected = Sets.newHashSet();

        for (Method method : methodsAnnotatedWith) {
            buildJSONDocObjectsCandidates(candidates, method.getReturnType(), method.getGenericReturnType());
//            Integer requestBodyParameterIndex = JSONDocUtils.getIndexOfParameterWithAnnotation(method, RequestBody.class);
//            if (requestBodyParameterIndex != -1) {
//                candidates.addAll(buildJSONDocObjectsCandidates(candidates, method.getParameterTypes()[requestBodyParameterIndex], method.getGenericParameterTypes()[requestBodyParameterIndex]));
//            }
        }

        // This is to get objects' fields that are not returned nor part of the body request of a method, but that are a field
        // of an object returned or a body  of a request of a method
        for (Class<?> clazz : candidates) {
            for (Field field : clazz.getDeclaredFields()) {
                subCandidates.addAll(buildJSONDocObjectsCandidates(subCandidates, field.getType(), field.getGenericType()));
            }
        }
        candidates.addAll(subCandidates);

        for (Class<?> clazz : candidates) {
            if (clazz.getPackage() != null) {
                for (String pkg : packages) {
                    if (clazz.getPackage().getName().contains(pkg)) {
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


    protected void copyProperties(Object source, Object dest, String... ignore) {
        try {
            Map<String, Object> describe = PropertyUtils.describe(source);

            if (ignore != null) {
                for (String s : ignore) {
                    describe.remove(s);
                }
            }
            BeanUtils.populate(dest, describe);
        } catch (Throwable e) {
            LOGGER.error("copyProperties: could not copy properties", e);
        }
    }

    /**
     * Once the ApiDoc has been initialized and filled with other data (version,
     * auth, etc) it's time to merge the documentation with JSONDoc annotation,
     * if existing.
     */
    @Override
    public ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc) {
        ApiDoc jsondocApiDoc = JSONDocApiDocBuilder.build(controller);
        copyProperties(jsondocApiDoc, apiDoc, "methods", "supportedversions", "auth");
        return apiDoc;
    }

    @Override
    public ApiMethodDoc initApiMethodDoc(Method method, Map<Class<?>, JSONDocTemplate> jsondocTemplates) {
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setPath(JaxRsPathBuilder.buildPath(method));
        apiMethodDoc.setVerb(JaxRsVerbBuilder.buildVerb(method));
        apiMethodDoc.setProduces(JaxRsProducesBuilder.buildProduces(method));
        apiMethodDoc.setConsumes(JaxRsConsumesBuilder.buildConsumes(method));
        apiMethodDoc.setHeaders(JaxRsHeaderBuilder.buildHeaders(method));
        apiMethodDoc.setPathparameters(JaxRsPathVariableBuilder.buildPathVariable(method));
        apiMethodDoc.setQueryparameters(JaxRsQueryParamBuilder.buildQueryParams(method));
        apiMethodDoc.setBodyobject(JaxRsRequestBodyBuilder.buildRequestBody(method));
        apiMethodDoc.setResponse(JaxRsResponseBuilder.buildResponse(method));
        apiMethodDoc.setResponsestatuscode(JaxRsResponseStatusBuilder.buildResponseStatusCode(method));

//        Integer index = JSONDocUtils.getIndexOfParameterWithAnnotation(method, RequestBody.class);
//        if (index != -1) {
//            apiMethodDoc.getBodyobject().setJsondocTemplate(jsondocTemplates.get(method.getParameterTypes()[index]));
//        }

        return apiMethodDoc;
    }

    @Override
    public ApiMethodDoc mergeApiMethodDoc(Method method, ApiMethodDoc apiMethodDoc) {
        if (method.isAnnotationPresent(ApiMethod.class) && method.getDeclaringClass().isAnnotationPresent(Api.class)) {
            ApiMethodDoc jsondocApiMethodDoc = JSONDocApiMethodDocBuilder.build(method);
            copyProperties(jsondocApiMethodDoc, apiMethodDoc, "path", "verb", "produces", "consumes", "headers", "pathparameters", "queryparameters", "bodyobject", "response", "responsestatuscode", "apierrors", "supportedversions", "auth", "displayMethodAs");
        }
        return apiMethodDoc;
    }

    @Override
    public ApiObjectDoc initApiObjectDoc(Class<?> clazz) {
        return JaxRsObjectBuilder.buildObject(clazz);
    }

    @Override
    public ApiObjectDoc mergeApiObjectDoc(Class<?> clazz, ApiObjectDoc apiObjectDoc) {
        if (clazz.isAnnotationPresent(ApiObject.class)) {
            ApiObjectDoc jsondocApiObjectDoc = JSONDocApiObjectDocBuilder.build(clazz);
            copyProperties(jsondocApiObjectDoc, apiObjectDoc);
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
