package com.project.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.project.util.jpa.BaseEntity;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BaseConfiguration {
	
	public Class<? extends BaseEntity> baseEntity();
	
	public String findNamedQuery() default "";
	
	public String findAllNamedQuery() default "";
	
	public String fileProperty() default "";
	
	public String[] findFilterExpressionItems() default {};
	
	public String[] findLazyProperties() default {};
	public String[] prepareUpdateLazyProperties() default {};
	public String[] activateLazyProperties() default {};
	public String[] deactivateLazyProperties() default {};
	
	public boolean detail() default false;
	
	public String[] masterDetailMBs() default {};
	
	public String[] shuttleListsNames() default {};
	
	public String[] relatedComboNamedQueries() default {};
	
	public String[] enumProperties() default {};
	
	public String saveSuccessMessage() default "geomais.global.msg.success.save";
	public String updateSuccessMessage() default "geomais.global.msg.success.update";
	public String activateSuccessMessge()  default "geomais.global.msg.success.activate";
	public String deactivateSuccessMessage() default "geomais.global.msg.success.deactivate";
	
	public boolean dynamicEntity() default false;
	
	public BaseAction[] actions();
	
}