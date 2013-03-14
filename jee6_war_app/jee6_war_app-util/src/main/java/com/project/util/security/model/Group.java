package com.project.util.security.model;

import java.util.List;


public interface Group {
	
	public String getGroupName();
	
	public String getGroupType();
	
	public List<? extends Role> getRoles();

}
