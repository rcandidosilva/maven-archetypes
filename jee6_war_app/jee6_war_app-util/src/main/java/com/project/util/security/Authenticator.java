package com.project.util.security;

import javax.inject.Inject;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;

import com.project.util.security.model.Group;
import com.project.util.security.model.Role;
import com.project.util.security.model.User;


@SuppressWarnings("all")
public class Authenticator extends BaseAuthenticator {

	@Inject Credentials credentials;
	
	@Inject Identity identity;
	
	@Inject AuthenticatorService service;
	
	public void authenticate() {
		User user = service.findUserByUsername(credentials.getUsername());
		
		if (user == null) {
			setStatus(AuthenticationStatus.FAILURE);
			return;
		}
		
		String password = user.getUserPassword();
		
		if (user.getUserName().toUpperCase().trim().equals(credentials.getUsername().toUpperCase().trim()) &&
	            password.equals(((PasswordCredential) credentials.getCredential()).getValue())) {
			
			if (user.getRoles() != null) {
				for (Role role : user.getRoles()) {
					if (role != null)
						identity.addRole(role.getRoleName(), null, null);
				}
			}
			if (user.getGroups() != null) {
				for (Group group : user.getGroups()) {
					if (group != null) {
						identity.addGroup(group.getGroupName(), group.getGroupType());
						if (group.getRoles() != null) {
							for (Role groupRole : group.getRoles()) {
								if (groupRole != null)
									identity.addRole(groupRole.getRoleName(), 
											group.getGroupName(), group.getGroupType());
							}
						}
					}
				}
			}
			
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(user.getUserId(), user.getUserName(), user));
		} else {
			setStatus(AuthenticationStatus.FAILURE);
		}
	}

}
