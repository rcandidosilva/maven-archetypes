package br.com.project.web.jsf;

import javax.inject.Inject;

import br.com.primesoft.jpf.annotation.BaseType;
import br.com.primesoft.jpf.service.BaseService;
import br.com.primesoft.jpf.web.jsf.BaseMB;
import br.com.project.service.ProjectService;

@SuppressWarnings("all")
public abstract class ProjectManagedBean extends BaseMB {

	@Inject @BaseType
	private ProjectService service;
	
	@Override
	public BaseService getBaseService() {
		return service;
	}
		
}