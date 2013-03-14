package br.com.project.service.sample;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import br.com.primesoft.jpf.exception.BaseBusinessException;
import br.com.primesoft.jpf.jpa.Entity;
import br.com.primesoft.jpf.security.AuthenticatorService;
import br.com.primesoft.jpf.security.model.User;
import br.com.project.jpa.sample.SampleEntity;
import br.com.project.service.ProjectServiceImpl;

@Local(SampleServiceImpl.class)
@Stateless
@SuppressWarnings("all")
public class SampleServiceImpl extends ProjectServiceImpl implements SampleService {

	@Override
	public Entity save(Entity entity) {
		SampleEntity sample = (SampleEntity) entity;
		List<Entity> list = listByNamedQuery("selectSampleByLogin", sample.getLogin());
		if (list.isEmpty()) {
			return super.save(sample);
		} else {
			throw new BaseBusinessException();
		}
		
	}
	
	@Override
	public User findUserByUsername(String username) {
		SampleEntity entity = null;
		if ("admin".equals(username)) {
			entity = new SampleEntity("Administrator", "admin@email.com", "admin", "admin", "admin", "all");
		} else {
			List<Entity> list = listByNamedQuery("selectSampleByLogin", username);
			if (!list.isEmpty()) {
				entity = (SampleEntity) list.get(0);
			}
		}
		return entity;
	}	
	
}
