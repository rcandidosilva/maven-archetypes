package com.project.util.service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.jboss.seam.transaction.TransactionScoped;
import org.jboss.seam.transaction.Transactional;

import com.project.util.exception.BaseSystemException;
import com.project.util.jpa.BaseEntity;
import com.project.util.jpa.DynamicEntity;
import com.project.util.jpa.Entity;
import com.project.util.web.model.Order;


@SuppressWarnings("all")
public abstract class BaseServiceImpl implements BaseService {

    protected abstract EntityManager getEntityManager();

    public List<? extends Entity> listAll(Class<? extends Entity> entity) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<? extends Entity> query = builder.createQuery(entity);
        query.from(entity);
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<? extends Entity> listAllPaginated(Class<? extends Entity> clazz, int firstResult, int maxResult, Map<String, String> filters, Order order){
        
        StringBuilder hql = new StringBuilder();
        hql.append("FROM " + clazz.getSimpleName() + " entity ");
        
        if(filters != null && !filters.isEmpty()){
            
            hql.append(" WHERE 1=1 ");
            Iterator it = filters.entrySet().iterator();
            
            while (it.hasNext()) {
                
                Map.Entry pairs = (Map.Entry)it.next();
                
                String key = (String) pairs.getKey();
                String value = (String) pairs.getValue();
                
                hql.append(" AND entity."+ key +" LIKE  :"+key);
            }
        }
        
        if(order != null && order.getField() != null 
                && !order.getField().equalsIgnoreCase("") && order.getDirection()!=null){
            
            hql.append(" ORDER BY entity."+order.getField()+" "+order.getDirection().toString());
        }
        
        Query query = getEntityManager().createQuery(hql.toString());
        
        if(filters != null && !filters.isEmpty()){
            
            Iterator it = filters.entrySet().iterator();
            
            while (it.hasNext()) {
                
                Map.Entry pairs = (Map.Entry)it.next();
                
                String key = (String) pairs.getKey();
                String value = (String) pairs.getValue();
                
                query.setParameter(key, "%"+value+"%");
            }
        }
        
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        
        return query.getResultList();
    }

    public Integer countAll(Class<? extends Entity> clazz) {

        String hql = "SELECT COUNT( entity.id ) FROM " + clazz.getSimpleName() + " entity";

        Query query = getEntityManager().createQuery(hql);
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }
    
    
    public Integer countAllPaginated(Class<? extends Entity> clazz, Map<String, String> filters){
        
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT COUNT( entity.id ) FROM " + clazz.getSimpleName() + " entity ");
        
        if(filters != null && !filters.isEmpty()){
            
            hql.append(" WHERE 1=1 ");
            Iterator it = filters.entrySet().iterator();
            
            while (it.hasNext()) {
                
                Map.Entry pairs = (Map.Entry)it.next();

                String key = (String) pairs.getKey();
                String value = (String) pairs.getValue();
                
                hql.append(" AND entity."+ key +" LIKE :"+key);
            }
        }
        
        Query query = getEntityManager().createQuery(hql.toString());
        
        if(filters != null && !filters.isEmpty()){
            
            Iterator it = filters.entrySet().iterator();
            
            while (it.hasNext()) {
                
                Map.Entry pairs = (Map.Entry)it.next();

                String key = (String) pairs.getKey();
                String value = (String) pairs.getValue();
                
                query.setParameter(key, "%"+value+"%");
            }
        }
        
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }
    

    public List<Entity> listByNamedQuery(String namedQuery) {

        Query query = getEntityManager().createNamedQuery(namedQuery);
        return (List<Entity>) query.getResultList();
    }

    protected BaseEntity getBaseEntity(Entity entity) {

        BaseEntity baseEntity = null;
        if (entity instanceof DynamicEntity) {
            baseEntity = ((DynamicEntity) entity).getBaseEntity();
        } else {
            baseEntity = (BaseEntity) entity;
        }
        return baseEntity;
    }

    public List<Entity> listByNamedQuery(String namedQuery, Object... params) {

        Query query = getEntityManager().createNamedQuery(namedQuery);
        if (params != null) {
            for (int i = 1; i <= params.length; i++) {
                query.setParameter(i, params[i - 1]);
            }
        }
        return query.getResultList();
    }

    public List<Entity> listByNamedQuery(String namedQuery, Map<String, Object> namedParams) {

        Query query = getEntityManager().createNamedQuery(namedQuery);
        if (namedParams != null) {
            for (String key : namedParams.keySet()) {
                query.setParameter(key, namedParams.get(key));
            }
        }
        return query.getResultList();
    }

    public Entity findById(Entity entity) {

        BaseEntity baseEntity = getBaseEntity(entity);
        baseEntity = getEntityManager().find(baseEntity.getClass(), baseEntity.getId());
        if (entity instanceof DynamicEntity) {
            ((DynamicEntity) entity).setBaseEntity(baseEntity);
        } else {
            entity = baseEntity;
        }
        initializeLazyProperties(baseEntity, entity.getLazyProperties());
        return baseEntity;
    }

    public Entity findById(Class clazz, Object id) {

        Entity entity = getEntityManager().find(clazz, id);
        initializeLazyProperties(entity, entity.getLazyProperties());
        return entity;
    }

    protected void initializeLazyProperties(Entity entity, String[] properties) {

        try {
            for (String lazyProperty : properties) {
                PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(entity, lazyProperty);
                if (descriptor != null) {
                    Object result = descriptor.getReadMethod().invoke(entity, new Object[0]);
                    Object property = PropertyUtils.getProperty(entity, lazyProperty);
                    if (Collection.class.isInstance(property)) {
                        ((Collection) property).iterator();
                    }
                }
            }
        } catch (Exception ex) {
            throw new BaseSystemException(ex);
        }
    }

    public Entity save(Entity entity) {

        BaseEntity baseEntity = getBaseEntity(entity);
        getEntityManager().persist(baseEntity);
        getEntityManager().flush();
        if (entity instanceof DynamicEntity) {
            ((DynamicEntity) entity).setBaseEntity(baseEntity);
        } else {
            entity = baseEntity;
        }
        return entity;
    }

    public List<Entity> save(List<Entity> listEntity) {

        List<Entity> listEntityUpdated = new ArrayList<Entity>();
        for (Entity entity : listEntity) {
            update(entity);
        }
        return listEntityUpdated;
    }

    public Entity update(Entity entity) {

        BaseEntity baseEntity = getBaseEntity(entity);
        baseEntity = getEntityManager().merge(baseEntity);
        getEntityManager().flush();
        if (entity instanceof DynamicEntity) {
            ((DynamicEntity) entity).setBaseEntity(baseEntity);
        } else {
            entity = baseEntity;
        }
        return entity;
    }

    public Entity merge(Entity entity) {

        return getEntityManager().merge(entity);
    }

    public void refresh(Entity entity) {

        getEntityManager().refresh(entity);
    }

    public Entity remove(Entity entity) {

        BaseEntity baseEntity = getBaseEntity(entity);
        baseEntity = getEntityManager().find(baseEntity.getClass(), baseEntity.getId());
        getEntityManager().remove(baseEntity);
        getEntityManager().flush();
        if (entity instanceof DynamicEntity) {
            ((DynamicEntity) entity).setBaseEntity(baseEntity);
        } else {
            entity = baseEntity;
        }
        return entity;
    }

}
