package com.project.util.security.permission;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.permission.PermissionResolver;

import com.project.util.security.SimpleUser;
import com.project.util.security.model.Permission;


public class SecurityPermissionResolver implements PermissionResolver {

	@Inject Identity identity;
	
	public boolean hasPermission(Object resource, String permission) {
		
		SimpleUser user = (SimpleUser) identity.getUser();
		if (user != null) {
			List<? extends Permission> permissions = user.getUser().getPermissions();
			if (permissions != null) {
				for (Permission p : permissions) {
					if (p.getPermission().equals(permission) && 
							p.getTarget().equals(resource)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void filterSetByAction(Set<Object> resources, String permission) {
		// TODO Auto-generated method stub
	}
	

}