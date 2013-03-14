package com.project.test.sample.selenium;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.project.test.util.SeleniumBaseTest;
import com.thoughtworks.selenium.DefaultSelenium;

@Ignore 
@RunWith(Arquillian.class)
public class SampleTest extends SeleniumBaseTest {
	
	@Deployment @OverProtocol("Servlet 3.0")
	public static Archive<?> doDeploy() throws Exception {
		//return createDeployment(SampleTest.class); 
		return null;
	}
	
	@Drone
	private DefaultSelenium browser;
	
	@Test
	public void testSomething() {
		System.out.println(browser);
		System.out.println("just testing...");
	}

}
