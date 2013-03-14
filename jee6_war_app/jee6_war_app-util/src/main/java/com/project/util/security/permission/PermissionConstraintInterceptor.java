package com.project.util.security.permission;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@PermissionConstraint
@Interceptor
public class PermissionConstraintInterceptor implements Serializable {

	@Inject SimplePermission current;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		Method method = ctx.getMethod();
		for (Annotation a : method.getDeclaredAnnotations()) {
			if (a instanceof PermissionConstraint) {
				current.setTarget(((PermissionConstraint) a).target());
				current.setPermission(((PermissionConstraint) a).permission());
			}
		}
		return ctx.proceed();
	}
	
}
