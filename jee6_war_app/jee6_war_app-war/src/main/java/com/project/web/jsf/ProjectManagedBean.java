package com.project.web.jsf;

import javax.inject.Inject;

import com.project.service.ProjectService;
import com.project.util.annotation.BaseType;
import com.project.util.service.BaseService;
import com.project.util.web.jsf.BaseMB;


@SuppressWarnings("all")
public abstract class ProjectManagedBean extends BaseMB {

	@Inject @BaseType
	private ProjectService service;
	
	@Override
	public BaseService getBaseService() {
		return service;
	}
		
}