package com.project.util.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.beanutils.PropertyUtils;

public class ReflectionUtil {

    public static Class getGenericPropertyType(Object bean, String property) throws Exception {

        Method method = PropertyUtils.getPropertyDescriptor(bean, property).getReadMethod();
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            Type type = ((ParameterizedType) returnType).getActualTypeArguments()[0];
            return (Class) type;
        }
        return null;
    }

    public static Object newGenericPropertyInstance(Object bean, String property) throws Exception {

        Class type = getGenericPropertyType(bean, property);
        if (type != null) {
            return type.newInstance();
        }
        return null;
    }

    public static Object createWrapperObject(Class wrapperClass, String value) throws Exception {

        Constructor constructor = wrapperClass.getConstructor(String.class);
        return constructor.newInstance(value);

    }

}