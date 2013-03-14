package com.project.test.sample.service;

import java.util.Calendar;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.test.util.BaseTest;


@RunWith(Arquillian.class)
public class SampleTest extends BaseTest {

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
//	
//	@Test
//	public void testCpfJaCadastrado() throws Exception {
//		Colaborador c1 = new Colaborador("Joao", "12312312312");
//		c1 = (Colaborador) ejb.save(c1);
//		Colaborador c2 = new Colaborador("Maria", "12312312312");
//		Exception cause = null;
//		try { 
//			c2 = (Colaborador) ejb.save(c2);
//		} catch (Exception e) {
//			cause = (Exception) e.getCause();
//		}
//		Assert.assertNotNull(c1);
//		Assert.assertNotNull(c1.getId());
//		Assert.assertTrue(c1.getId() > 0);
//		Assert.assertNotNull(cause);
//		Assert.assertTrue(cause instanceof BusinessException);
//		Assert.assertNotNull(((BusinessException) cause).getMessagesKey());
//		Assert.assertNotNull(((BusinessException) cause).getMessagesKey().get(0));
//	}

}
