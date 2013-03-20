package br.com.project.web.security.sample;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

import com.project.util.security.permission.PermissionConstraint;

@ViewConfig
public interface SamplePages {

	static enum Pages1 {
		
        @FacesRedirect
        @ViewPattern("/*")
        @AccessDeniedView("/error.xhtml")
        @LoginView("/login.xhtml")
        ALL,

        @PermissionConstraint(target="sample", permission="all")
		@ViewPattern("/pages/sample/*")
		SAMPLE,
		
	}
	
}
