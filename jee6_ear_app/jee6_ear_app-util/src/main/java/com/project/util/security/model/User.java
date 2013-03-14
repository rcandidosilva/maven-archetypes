package com.project.util.security.model;

import java.util.List;

public interface User {

	public String getUserId();
	
	public String getUserName();
	
	public String getUserPassword();
	
	public List<? extends Role> getRoles();
	
	public List<? extends Group> getGroups();
	
	public List<? extends Permission> getPermissions();
	
}
