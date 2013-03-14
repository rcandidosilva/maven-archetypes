package com.project.util.security.permission;

import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import org.jboss.seam.security.events.AuthorizationCheckEvent;

public class PermissionConstraintHandler {
	
	@Inject SimplePermission current;
	
	public void observeAfterRestoreView(@Observes AuthorizationCheckEvent e) {
		for (Annotation a : e.getBindings()) {
			if (a instanceof PermissionConstraint) {
				current.setTarget(((PermissionConstraint) a).target());
				current.setPermission(((PermissionConstraint) a).permission());
			}
		}
	}

	@Secures @PermissionConstraint
	public boolean hasFullAccessPermission(Identity identity) {
		if (current.getTarget() != null && current.getPermission() != null) {
			return identity.hasPermission(current.getTarget(), current.getPermission());
		} 
		return true;
	}	

}