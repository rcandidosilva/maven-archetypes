package com.project.util.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.project.util.jpa.Entity;


public interface BaseService extends Serializable {
	
	public List<? extends Entity> listAll(Class<? extends Entity> entity);
	
	public List<Entity> listByNamedQuery(String namedQuery, Map<String, Object> namedParams);
	
	public List<Entity> listByNamedQuery(String namedQuery, Object... params);	
	
	public List<Entity> listByNamedQuery(String namedQuery);
	
	public Entity findById(Entity entity);
	
	public Entity findById(Class clazz, Object id);
	
	public Entity save(Entity entity);
	
	public List<Entity> save(List<Entity> listEntity);
	
	public Entity update(Entity entity);

	public Entity merge(Entity entity);
	
	public void refresh(Entity entity);
	
	public Entity remove(Entity entity);
	
}