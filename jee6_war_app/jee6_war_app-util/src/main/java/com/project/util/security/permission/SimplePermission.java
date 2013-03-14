package com.project.util.security.permission;

import javax.enterprise.context.RequestScoped;

import com.project.util.security.model.Permission;


@RequestScoped
public class SimplePermission implements Permission {

	private String permission;
	private String target;
	
	public SimplePermission() {
		super();
	}
	
	public SimplePermission(String permission, String target) {
		this.permission = permission;
		this.target = target;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}
	
}