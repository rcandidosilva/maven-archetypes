package com.project.test.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.project.util.test.TestUtils;


public abstract class SeleniumBaseTest {

	public static EnterpriseArchive createDeployment(Class<? extends BaseTest> testClass) throws Exception {
		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class,
				"callisto-ear.ear");

		ear.addAsLibraries(TestUtils.getMavenDependency(true,
				"br.com.callisto:callisto-infra",
				"org.seleniumhq.selenium:selenium-java"));
		
		ear.addAsLibrary(ShrinkWrap.create(JavaArchive.class, "callisto-jpa.jar")
				.addPackage("br.com.callisto.jpa")
				.addAsManifestResource(ClassLoader.getSystemResource("persistence.xml"), "persistence.xml")
				.addAsManifestResource(ClassLoader.getSystemResource("beans.xml"), "beans.xml"));
		
		ear.addAsLibrary(ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(SeleniumBaseTest.class, testClass));
		
		ear.addAsModules(TestUtils.getMavenDependency(true,
				"br.com.callisto:callisto-ejb", "br.com.callisto:callisto-web:war"));
		ear.addAsManifestResource(
				ClassLoader.getSystemResource("application.xml"),
				"application.xml");
		ear.addAsManifestResource(ClassLoader.getSystemResource("test-ds.xml"),
				"test-ds.xml");

		return ear;
	}

}
