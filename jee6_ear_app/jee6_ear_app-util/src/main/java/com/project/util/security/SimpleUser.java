package com.project.util.security;

import com.project.util.security.model.User;

public class SimpleUser implements org.picketlink.idm.api.User {

	private String id;
	private String key;
	private User user;
	
	public SimpleUser(String id, String key, User user) {
		this.id = id;
		this.key = key;
		this.user = user;
	}
	
	public String getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}

	public User getUser() {
		return user;
	}
}
