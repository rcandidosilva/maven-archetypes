package com.project.util.security.role;

import javax.enterprise.context.RequestScoped;

import com.project.util.security.model.Role;


@RequestScoped
public class SimpleRole implements Role {

	private String roleName;
	
	public SimpleRole() {
		super();
	}
	
	public SimpleRole(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
