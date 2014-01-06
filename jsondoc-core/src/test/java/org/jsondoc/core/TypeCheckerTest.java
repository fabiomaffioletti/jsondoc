package org.jsondoc.core;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class TypeCheckerTest {
	private static Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("org.jsondoc.core")));;
	
	@Test
	public void test() {
		Class<?> methodSignatures = ReflectionUtils.forName("org.jsondoc.core.MethodSignatures");
		Assert.assertNotNull(methodSignatures);
		
		try {
			Method[] methods = methodSignatures.getDeclaredMethods();
			for (Method method : methods) {
				System.out.println(method.toGenericString());
				System.out.println(checkReturnType(method));
				
				System.out.println("---------------------------------------------------------\n");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}
	
	private ReturnTypePojo checkReturnType(Method method) {
		ReturnTypePojo returnTypePojo = new ReturnTypePojo();
		returnTypePojo.setClazz(method.getReturnType());
		
		if(Map.class.isAssignableFrom(method.getReturnType())) {
			returnTypePojo.setMultiple(false);
			
			if(method.getGenericReturnType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
				Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
				Type mapValueType = parameterizedType.getActualTypeArguments()[1];
				Class<?> mapKeyClazz = (Class<?>) mapKeyType;
				Class<?> mapValueClazz = (Class<?>) mapValueType;
				returnTypePojo.setMapKeyObject(mapKeyClazz);
				returnTypePojo.setMapValueObject(mapValueClazz);
			}
			
			returnTypePojo.setObject(getObjectNameFromAnnotatedClass(returnTypePojo.getClazz(), false));

		} else if(Collection.class.isAssignableFrom(method.getReturnType())) {
			returnTypePojo.setMultiple(true);
			
			if(method.getGenericReturnType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
				Type type = parameterizedType.getActualTypeArguments()[0];
				Class<?> clazz = (Class<?>) type;
				returnTypePojo.setClazz(clazz);
			}
			returnTypePojo.setObject(getObjectNameFromAnnotatedClass(returnTypePojo.getClazz(), true));
		
		} else if(method.getReturnType().isArray()) {
			returnTypePojo.setMultiple(true);
			Class<?> classArr = method.getReturnType();
			returnTypePojo.setObject(getObjectNameFromAnnotatedClass(classArr.getComponentType(), false));
			
		} else {
			returnTypePojo.setMultiple(false);
			returnTypePojo.setObject(getObjectNameFromAnnotatedClass(returnTypePojo.getClazz(), false));
		}
		
		return returnTypePojo;
	}
	
	private String getObjectNameFromAnnotatedClass(Class<?> clazz, boolean collection) {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
		if(annotatedClass.isAnnotationPresent(ApiObject.class)) {
			return annotatedClass.getAnnotation(ApiObject.class).name();
		}
		return clazz.getSimpleName().toLowerCase();
	}
}
