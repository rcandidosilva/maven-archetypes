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

    public static Method getMethodByName(Class clazz, String methodName) {

        Method methodList[] = clazz.getDeclaredMethods();

        for (int i = 0; i < methodList.length; i++) {
            Method method = methodList[i];
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Object getObjectByInvokeMethod(Object bean, String methodName, Object... args) throws Exception{

        Method method = getMethodByName(bean.getClass(), methodName);
        Object object;

        if (method.getParameterTypes().length > 0){
            
            object = method.invoke(bean, args);
        }else{
            
            object = method.invoke(bean);
        }
        return object;
    }

}
