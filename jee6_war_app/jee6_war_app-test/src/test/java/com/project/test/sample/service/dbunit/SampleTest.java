package com.project.test.sample.service.dbunit;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.test.util.DBUnitBaseTest;


@RunWith(Arquillian.class)
public class SampleTest extends DBUnitBaseTest {

	public InputStream getInsertDataSet() {
		return getClass().getResourceAsStream("Colaborador-insert-dataset.xml");
	}	
	
	public InputStream getDeleteDataSet() {
		return getClass().getResourceAsStream("Colaborador-delete-dataset.xml");
	}
	
//	@Deployment
//	public static Archive<?> doDeploy() throws Exception {
//		return createDeployment(SampleTest.class).addClasses(ColaboradorEJB.class,
//				ColaboradorEJBImpl.class);
//	}
//
//	@Inject
//	private ColaboradorEJB ejb;
//
//	@Test
//	public void testListAll() throws Exception {
//		List list = ejb.findAll(Colaborador.class);
//		System.out.println(list);
//		Assert.assertNotNull(list);
//		Assert.assertTrue(list.size() > 0);
//	}
//
//	@Test
//	public void testSaveSuccessful() throws Exception {
//		Colaborador c = new Colaborador("Rodrigo", "01441141122");
//		c = (Colaborador) ejb.save(c);
//		Assert.assertNotNull(c);
//		Assert.assertNotNull(c.getId());
//		Assert.assertTrue(c.getId() > 0);
//		Assert.assertNotNull(c.getDataCadastro());
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, -1);
//		Assert.assertTrue(c.getDataCadastro().after(cal.getTime()));
//		Assert.assertTrue(c.isAtivo());
//	}
//	
//	@Test
//	public void testUpdateSucessful() throws Exception {
//		Colaborador inserted = new Colaborador("Rafael", "11111111111");
//		inserted = (Colaborador) ejb.save(inserted);
//		
//		Colaborador updated = inserted;
//		
//		updated.setNome("Joao");
//		updated.setCpf("22222222222");
//		updated.setAtivo(false);
//		updated = (Colaborador) ejb.update(updated);
//	}
//	
//	@Test
//	public void testDeleteSuccesful() throws Exception {
//		Colaborador c1 = new Colaborador("Joao", "55555555555");
//		ejb.save(c1);
//		ejb.delete(c1);
//	}
	
}