package com.project.util.annotation;

public @interface BaseAction {

	public enum ActionType { SAVE, UPDATE, DELETE, LIST, LIST_ALL, PREPARE_SAVE, PREPARE_UPDATE, PREPARE_LIST }
	
	public ActionType type();
	
	public BaseQuery[] queries() default {};
	
	public BaseNavigation navigation();
	
}
