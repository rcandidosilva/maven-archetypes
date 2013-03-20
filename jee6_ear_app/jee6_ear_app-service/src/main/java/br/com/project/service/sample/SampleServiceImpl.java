package br.com.project.service.sample;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.project.jpa.sample.SampleEntity;
import br.com.project.service.ProjectServiceImpl;

import com.project.util.exception.BaseBusinessException;
import com.project.util.jpa.Entity;
import com.project.util.security.model.User;

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
