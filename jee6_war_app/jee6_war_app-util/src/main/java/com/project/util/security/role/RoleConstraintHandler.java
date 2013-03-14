package com.project.util.security.role;

import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import org.jboss.seam.security.events.AuthorizationCheckEvent;

public class RoleConstraintHandler {
	
	@Inject SimpleRole current;
	
	public void observeAfterRestoreView(@Observes AuthorizationCheckEvent e) {
		for (Annotation a : e.getBindings()) {
			if (a instanceof RoleConstraint) {
				current.setRoleName(((RoleConstraint) a).role());
			}
		}
	}

	@Secures @RoleConstraint
	public boolean hasFullAccessPermission(Identity identity) {
		if (current.getRoleName() != null) {
			return identity.hasRole(current.getRoleName(), current.getRoleName(), current.getRoleName());
		} 
		return true;
	}	

}