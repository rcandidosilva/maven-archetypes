package br.com.project.web.security.sample;

import javax.enterprise.event.Observes;

import org.jboss.seam.security.events.PostAuthenticateEvent;

public class SampleEventHandler {
	
	public void observeAfterRestoreView(@Observes PostAuthenticateEvent evt) {
		// TODO
	}

}
