package com.project.util.jpa;

public abstract class DynamicEntity implements Entity {
	
	public abstract void setBaseEntity(BaseEntity entity);
	
	public abstract BaseEntity getBaseEntity();
	
}
