package com.project.util.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.international.status.Messages;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

import com.project.util.annotation.BaseAction;
import com.project.util.annotation.BaseConfiguration;
import com.project.util.annotation.BaseDataModel;
import com.project.util.annotation.BaseNavigation;
import com.project.util.annotation.BaseQuery;
import com.project.util.annotation.BaseAction.ActionType;
import com.project.util.jpa.BaseEntity;
import com.project.util.jpa.DynamicEntity;
import com.project.util.jpa.Entity;
import com.project.util.reflection.ReflectionUtil;
import com.project.util.service.BaseService;
import com.project.util.web.model.PrimeDataModel;


@BaseConfiguration(baseEntity = BaseEntity.class, actions = {
        @BaseAction(type = ActionType.PREPARE_SAVE, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel),
        @BaseAction(type = ActionType.PREPARE_UPDATE, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel),
        @BaseAction(type = ActionType.SAVE, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel),
        @BaseAction(type = ActionType.UPDATE, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel),
        @BaseAction(type = ActionType.DELETE, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel),
        @BaseAction(type = ActionType.LIST_ALL, navigation = @BaseNavigation(error = "/index"), methods = @BaseDataModel)})
@SuppressWarnings("all")
public abstract class BaseMB implements Serializable {

    @Inject
    protected Messages messages;

    protected Entity entity;

    protected Entity masterEntity;

    protected List<? extends Entity> listEntityChildren;

    protected List<? extends Entity> listEntity;

    protected LazyDataModel<BaseEntity> dataModel;
    
    protected Map<String, Object> listQuery;

    protected List<SelectItem> enumLists[] = new List[0];

    protected String relatedComboNamedQueries[] = new String[0];

    protected String relatedComboListItems[] = new String[0];

    protected Long shuttleListIDs[][] = new Long[0][0];

    protected String findFilterExpressionItems[];

    public enum PageMode {
        SAVE, UPDATE
    }

    private PageMode pageMode = PageMode.SAVE;

    public abstract BaseService getBaseService();

    @PostConstruct
    public void init() throws Exception {

        entity = createNewEntity();
        findFilterExpressionItems = getBaseConfiguration().findFilterExpressionItems();
        listQuery = createListQuery();
    }

    public Map<String, Object> createListQuery() throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        BaseAction[] actions = getBaseConfiguration().actions();
        for (BaseAction action : actions) {
            BaseQuery[] queries = action.queries();
            for (BaseQuery query : queries) {
                if (query.isPickList()) {
                    result.put(query.id(), new DualListModel<Entity>());
                } else if (query.isMasterDetail()) {
                    result.put(query.id(), new ArrayList<Entity>());
                }
            }
        }
        return result;
    }

    public BaseConfiguration getBaseConfiguration() throws Exception {

        return getBaseConfiguration(getClass());
    }

    public BaseConfiguration getBaseConfiguration(Class clazz) throws Exception {

        String className = clazz.getName();
        int index = className.indexOf('$');
        index = index >= 0 ? index : className.length();
        className = StringUtils.left(className, index);
        Class targetClass = getClass().getClassLoader().loadClass(className);
        if (targetClass.isAnnotationPresent(BaseConfiguration.class)) {
            return (BaseConfiguration) targetClass.getAnnotation(BaseConfiguration.class);
        }
        return null;
    }

    public BaseAction getAction(BaseConfiguration conf, ActionType type) {

        BaseAction[] actions = conf.actions();
        for (BaseAction action : actions) {
            if (type.equals(action.type())) {
                return action;
            }
        }
        return null;
    }

    public BaseAction getAction(ActionType type) throws Exception {

        BaseAction result = getAction(getBaseConfiguration(), type);
        if (result == null) {
            result = getAction(getBaseConfiguration(BaseMB.class), type);
        }
        return result;
    }

    protected Entity createNewEntity() throws Exception {

        return getBaseConfiguration().baseEntity().newInstance();
    }

    public String prepareListAction() throws Exception {

        BaseAction action = getAction(ActionType.PREPARE_LIST);
        try {
            loadQueryLists(action);
            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    public String prepareSaveAction() throws Exception {

        BaseAction action = getAction(ActionType.PREPARE_SAVE);
        try {
            entity = createNewEntity();
            pageMode = PageMode.SAVE;
            loadQueryLists(action);
            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    /*
     * 
     * TODO COMENTAR
     */
    public void prepareUpdate() throws Exception {

        BaseAction action = getAction(ActionType.PREPARE_UPDATE);
        try {
            Class entityClass = getBaseConfiguration().baseEntity();
            entity = getBaseService().findById(entityClass, getEntityId());
            pageMode = PageMode.UPDATE;
            loadQueryLists(action);
            JSFUtil.generateMessages(entity);
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
        }
    }

    public String prepareUpdateAction() throws Exception {

        BaseAction action = getAction(ActionType.PREPARE_UPDATE);
        try {
            Class entityClass = getBaseConfiguration().baseEntity();
            entity = getBaseService().findById(entityClass, getEntityId());
            pageMode = PageMode.UPDATE;
            loadQueryLists(action);
            JSFUtil.generateMessages(entity);
            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    public String listAction() throws Exception {

        BaseAction action = getAction(ActionType.LIST);
        try {
            loadQueryLists(action);
            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    /*
     * TODO - COMENTAR
     */
    public void listAll() throws Exception {

        BaseAction action = getAction(ActionType.LIST_ALL);
        try {
            entity = createNewEntity();
            listEntity = getBaseService().listAll(getBaseConfiguration().baseEntity());
            if (listEntity.size() > 0) {
                entity = listEntity.get(0);
            }
            loadQueryLists(action);
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
        }
    }

    public void teste2() throws Exception {
        
        BaseAction action = getAction(ActionType.LIST_ALL);
        BaseDataModel baseMethods = action.methods();
        try {
            
            dataModel = new PrimeDataModel(getBaseService(), getBaseConfiguration().baseEntity(), baseMethods.method(), baseMethods.methodCount());
            
            /*
            if(dataModel.size()>0){
                 entity = dataModel.get(0);
            }
             */
            
            loadQueryLists(action);

        } catch (Exception ex) {
            JSFUtil.handleException(ex);
        }
    }

    public String listAllAction() throws Exception {

        BaseAction action = getAction(ActionType.LIST_ALL);
        try {
            entity = createNewEntity();
            listEntity = getBaseService().listAll(getBaseConfiguration().baseEntity());
            if (listEntity.size() > 0) {
                entity = listEntity.get(0);
            }
            loadQueryLists(action);
            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    public String saveAction() throws Exception {

        BaseAction action = getAction(ActionType.SAVE);
        try {
            if (getBaseConfiguration().detail()) {
                associateMasterEntity();
            }

            entity = getBaseService().save(entity);
            checkRefreshList();
            loadQueryLists(action);

            messages.info("Salvo com sucesso");
            return action.navigation().success();
        } catch (EJBException ejb) {
            JSFUtil.handleException(ejb);
            return null;
        } catch (Exception ex) {

            return action.navigation().error();
        }
    }

    public String updateAction() throws Exception {

        BaseAction action = getAction(ActionType.UPDATE);
        try {
            if (getBaseConfiguration().detail()) {
                associateMasterEntity();
            }
            loadQueryLists(action);
            entity = getBaseService().update(entity);
            messages.info("{0} atualizado com sucesso ", entity);
            checkRefreshList();
            return action.navigation().success();

        } catch (Exception ex) {
            messages.warn("{0} j� existente", entity);
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    public String deleteAction() throws Exception {

        BaseAction action = getAction(ActionType.DELETE);
        try {
            loadQueryLists(action);
            String t = entity.toString();
            entity = getBaseService().remove(entity);
            checkRefreshList();
            messages.info(" {0} exclu�do com sucesso", t);

            return action.navigation().success();
        } catch (Exception ex) {
            JSFUtil.handleException(ex);
            return action.navigation().error();
        }
    }

    protected void checkRefreshList() throws Exception {

        String refresh = JSFUtil.getRequestParameter("refresh-list");
        if (Boolean.parseBoolean(refresh)) {
            listAllAction();
        }
    }

    protected void loadQueryLists(BaseAction action) throws Exception {

        listQuery = createListQuery();
        BaseQuery[] queries = action.queries();
        for (BaseQuery query : queries) {
            List<Entity> list = new ArrayList<Entity>();
            if (!"".equals(query.query()))
                list = getBaseService().listByNamedQuery(query.query());
            if (list != null) {
                if (query.isPickList()) {
                    DualListModel<Entity> listModel = new DualListModel<Entity>(list, new ArrayList<Entity>());
                    listQuery.put(query.id(), listModel);
                } else {
                    listQuery.put(query.id(), list);
                }
            }
        }
    }

    public PageMode getPageMode() {

        return pageMode;
    }

    public Entity getEntity() {

        return entity;
    }

    public void setEntity(Entity entity) {

        this.entity = entity;
    }

    public List<? extends Entity> getListEntity() {

        return listEntity;
    }

    public void setListEntity(List<? extends Entity> listEntity) {

        this.listEntity = listEntity;
    }

    public Long[][] getShuttleListIDs() {

        return shuttleListIDs;
    }

    public void setShuttleListIDs(Long[][] shuttleListIDs) {

        this.shuttleListIDs = shuttleListIDs;
    }

    public void setRelatedComboNamedQueries(String relatedComboNamedQueries) {

        if (relatedComboNamedQueries != null) {
            this.relatedComboNamedQueries = relatedComboNamedQueries.split(",");
        }
    }

    public void setRelatedComboListItems(String relatedComboListItems) {

        if (relatedComboListItems != null) {
            this.relatedComboListItems = relatedComboListItems.split(",");
        }

    }

    public void loadRelatedCombos(ActionEvent event) throws Exception {

        for (int i = 0; i < relatedComboNamedQueries.length; i++) {
            List<Entity> list = getBaseService().listByNamedQuery(relatedComboNamedQueries[i], entity);
            List<SelectItem> listItems = JSFUtil.getListItems(list);
            PropertyUtils.setProperty(this, relatedComboListItems[i], listItems);
        }
    }

    protected void loadMasterDetailBeans() throws Exception {

        String[] masterDetailMBs = getBaseConfiguration().masterDetailMBs();
        if (masterDetailMBs != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExpressionFactory factory = context.getApplication().getExpressionFactory();
            ELContext elContext = context.getELContext();

            for (String name : masterDetailMBs) {
                ValueExpression ex = factory.createValueExpression(elContext, "#{" + name + "}", BaseMB.class);
                BaseMB mb = (BaseMB) ex.getValue(elContext);
                mb.setMasterEntity(this.entity);
                // mb.findAction();
            }

        }
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

    protected Object getEntityId() throws Exception {

        Class entityClass = getBaseConfiguration().baseEntity();
        String idValue = JSFUtil.getRequestParameter("id");
        Class idClass = PropertyUtils.getPropertyType(entity, "id");
        return ReflectionUtil.createWrapperObject(idClass, idValue);
    }

    public void setMasterEntity(Entity masterEntity) {

        this.masterEntity = masterEntity;
    }

    public Entity getMasterEntity() {

        return masterEntity;
    }

    public void associateMasterEntity() {

    }

    public String[] getFindFilterExpressionItems() {

        return findFilterExpressionItems;
    }

    public void setFindFilterExpressionItems(String[] findFilterExpressionItems) {

        this.findFilterExpressionItems = findFilterExpressionItems;
    }

    public List<SelectItem>[] getEnumLists() {

        return enumLists;
    }

    public Map<String, Object> getListQuery() {

        return listQuery;
    }

    public void setListQuery(Map<String, Object> listQuery) {

        this.listQuery = listQuery;
    }

    public void setPageMode(PageMode pageMode) {

        this.pageMode = pageMode;
    }

    
    public LazyDataModel<BaseEntity> getDataModel() {
    
        return dataModel;
    }

    
    public void setDataModel(LazyDataModel<BaseEntity> dataModel) {
    
        this.dataModel = dataModel;
    }


}
