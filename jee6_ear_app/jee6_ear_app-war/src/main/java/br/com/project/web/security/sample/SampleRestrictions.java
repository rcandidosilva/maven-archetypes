package br.com.project.web.security.sample;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;


public class SampleRestrictions {

	@Secures @SampleAdmin
	public boolean isAdmin(Identity identity) {
		return identity.hasRole("Administrador", null, null) || 
				identity.hasRole("Administrador", "Administrador", "Administrador");
	}

	
}
