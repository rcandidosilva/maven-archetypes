package com.project.util.security;

import com.project.util.security.model.User;

public interface AuthenticatorService {

	public User findUserByUsername(String username);
	
}
