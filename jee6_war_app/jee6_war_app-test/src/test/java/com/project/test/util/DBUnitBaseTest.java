package com.project.test.util;

import java.io.InputStream;

import org.dbunit.JndiDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;

import com.project.util.test.TestUtils;


@SuppressWarnings("all")
public abstract class DBUnitBaseTest extends BaseTest {
	
	public static JavaArchive createDeployment(Class<? extends BaseTest> testClass) throws Exception {
		JavaArchive archive = BaseTest.createDeployment(DBUnitBaseTest.class).merge(
				TestUtils.getArtifacts("org.dbunit:dbunit"),
				Filters.exclude("/org/dbunit/ant/.*|/org/dbunit/ext/.*"));
		archive.addClasses(DBUnitBaseTest.class, testClass);
		return archive.merge(
				TestUtils.getArtifacts("org.apache.poi:poi", "org.slf4j:slf4j-api"));
	}

	public abstract InputStream getInsertDataSet();
	
	public abstract InputStream getDeleteDataSet();

	@Before
	public void doInserts() throws Exception {
		InputStream insertDataSet = getInsertDataSet();
		if (insertDataSet != null) {
			JndiDatabaseTester dbTester = new JndiDatabaseTester("java:/callisto-ds");
			dbTester.setSchema("WEB");
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(insertDataSet);
			DatabaseOperation.INSERT.execute(dbTester.getConnection(), dataSet);
		}
	}
	
	@After
	public void doDeletes() throws Exception {
		InputStream deleteDataSet = getInsertDataSet();
		if (deleteDataSet != null) {
			JndiDatabaseTester dbTester = new JndiDatabaseTester("java:/callisto-ds");
			dbTester.setSchema("WEB");
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(deleteDataSet);
			DatabaseOperation.DELETE_ALL.execute(dbTester.getConnection(), dataSet);
		}
	}

}
