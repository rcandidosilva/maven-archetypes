package com.project.test.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public abstract class BaseTest {

	public static JavaArchive createDeployment(Class<? extends BaseTest> testClass) throws Exception {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"callisto-test.jar");
		jar.addPackages(true, "br.com.callisto.infra.ejb");
		jar.addPackages(true, "br.com.callisto.infra.exception");
		jar.addPackages(true, "br.com.callisto.infra.jpa");
		jar.addPackages(true, "br.com.callisto.infra.util");
		jar.addPackages(true, "br.com.callisto.infra.jpa");
		jar.addPackages(true, "br.com.callisto.jpa");
//		jar.addClasses(CallistoEJB.class, CallistoEJBImpl.class);
		jar.addClasses(BaseTest.class, testClass);
		jar.addAsManifestResource(ClassLoader.getSystemResource("test-ds.xml"), "test-ds.xml");
		jar.addAsManifestResource(ClassLoader.getSystemResource("persistence.xml"), "persistence.xml");
		jar.addAsManifestResource(ClassLoader.getSystemResource("beans.xml"), "beans.xml");
		jar.addAsManifestResource(ClassLoader.getSystemResource("ejb-jar.xml"), "ejb-jar.xml");
		//jar.merge(TestUtils.getArtifacts("commons-beanutils:commons-beanutils"));
		return jar;
	}

}
