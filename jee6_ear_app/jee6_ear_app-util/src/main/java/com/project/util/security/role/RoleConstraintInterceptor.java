package com.project.util.security.role;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@RoleConstraint
@Interceptor
public class RoleConstraintInterceptor implements Serializable {

	@Inject SimpleRole current;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		Method method = ctx.getMethod();
		for (Annotation a : method.getDeclaredAnnotations()) {
			if (a instanceof RoleConstraint) {
				current.setRoleName(((RoleConstraint) a).role());
			}
		}
		return ctx.proceed();
	}
	
}
