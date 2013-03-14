package br.com.project.web.jsf.sample;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.primesoft.jpf.annotation.BaseAction;
import br.com.primesoft.jpf.annotation.BaseConfiguration;
import br.com.primesoft.jpf.annotation.BaseNavigation;
import br.com.primesoft.jpf.annotation.BaseAction.ActionType;
import br.com.primesoft.jpf.service.BaseService;
import br.com.project.jpa.sample.SampleEntity;
import br.com.project.service.sample.SampleService;
import br.com.project.web.jsf.ProjectManagedBean;

@BaseConfiguration(baseEntity=SampleEntity.class, actions={
	@BaseAction(type = ActionType.PREPARE_SAVE, navigation = @BaseNavigation(success = "/pages/sample/save", error = "/index")),
	@BaseAction(type = ActionType.PREPARE_UPDATE, navigation = @BaseNavigation(success = "/pages/sample/update", error = "/index")),
	@BaseAction(type = ActionType.SAVE, navigation = @BaseNavigation(success = "/pages/sample/list?refresh-list=true", error = "/index")),
	@BaseAction(type = ActionType.UPDATE, navigation = @BaseNavigation(success = "/pages/sample/list?refresh-list=true", error = "/index")),
	@BaseAction(type = ActionType.LIST_ALL, navigation = @BaseNavigation(success = "/pages/sample/list", error = "/index"))
})
@Named
@SessionScoped
@SuppressWarnings("all")
public class SampleMB extends ProjectManagedBean {
	
	@Inject
	private SampleService service;
	
	@Override
	public BaseService getBaseService() {
		return service;
	}

}