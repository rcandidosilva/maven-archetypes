package com.project.web.security.sample;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.security.NotLoggedInException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;

@HandlesExceptions
public class SampleExceptionHandler {

	@Inject FacesContext facesContext;

	public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> evt) {
		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Voce nao tem privilegios para executar esta operacao",""));
		evt.handled();
	}
	
	public void handleNotLoggedInException(@Handles CaughtException<NotLoggedInException> evt) {
		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Voce ainda nao encontra-se logado",""));
		evt.handled();
	}

}