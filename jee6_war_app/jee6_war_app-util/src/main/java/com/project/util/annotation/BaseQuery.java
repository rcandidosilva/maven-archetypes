package com.project.util.annotation;

public @interface BaseQuery {

	public String id();
	
	public String query() default "";
	
	public boolean isPickList() default false;
	
	public boolean isMasterDetail() default false;
	
	public String label() default "descricao";
	
	public String value() default "id";
	
}
