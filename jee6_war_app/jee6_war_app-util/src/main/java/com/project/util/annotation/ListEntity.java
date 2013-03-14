package com.project.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import com.project.util.jpa.Entity;


@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface ListEntity {
	
	@Nonbinding
	public String baseEJBjndi() default "";
	
	@Nonbinding
	public Class<? extends Entity> baseEntity() default Entity.class;

}
