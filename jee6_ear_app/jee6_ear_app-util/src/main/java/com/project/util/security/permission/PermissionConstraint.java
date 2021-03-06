package com.project.util.security.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import org.jboss.seam.security.annotations.SecurityBindingType;

@InterceptorBinding
@SecurityBindingType
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
public @interface PermissionConstraint {

	@Nonbinding String target() default "";
	@Nonbinding String permission() default "";
	
}
