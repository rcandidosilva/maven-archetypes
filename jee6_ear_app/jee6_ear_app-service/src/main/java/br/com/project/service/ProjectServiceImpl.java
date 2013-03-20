package br.com.project.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.project.util.annotation.BaseType;
import com.project.util.service.BaseServiceImpl;

@Local(ProjectService.class)
@Stateless
@BaseType
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;
	
	@Override
	protected EntityManager getEntityManager() {
		return manager;
	}
	
}
