package com.project.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.project.util.annotation.BaseType;
import com.project.util.service.BaseServiceImpl;


@BaseType
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	protected EntityManager getEntityManager() {
		return manager;
	}
	
}
