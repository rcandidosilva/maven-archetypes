package com.project.util.security;

import javax.enterprise.event.Observes;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.view.config.ViewConfigStore;
import org.jboss.seam.security.events.NotAuthorizedEvent;

public class EventHandler {
	
    @Inject
    private ViewConfigStore viewConfigStore;

	public void notAuthorizedEvent(@Observes NotAuthorizedEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = context.getViewRoot();
        final PhaseId currentPhase = context.getCurrentPhaseId();
        if (!context.getResponseComplete() && PhaseId.RENDER_RESPONSE.equals(currentPhase)) {
            AccessDeniedView accessDeniedView = viewConfigStore.getAnnotationData(viewRoot.getViewId(), AccessDeniedView.class);
            if (accessDeniedView == null || accessDeniedView.value() == null || accessDeniedView.value().isEmpty()) {
                context.getExternalContext().setResponseStatus(401);
                context.responseComplete();
                return;
            }
            String accessDeniedViewId = accessDeniedView.value();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, "", accessDeniedViewId);
            context.renderResponse();
        }
	}
	
}
