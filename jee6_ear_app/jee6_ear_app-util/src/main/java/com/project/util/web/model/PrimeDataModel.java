package com.project.util.web.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.project.util.jpa.BaseEntity;
import com.project.util.reflection.ReflectionUtil;
import com.project.util.service.BaseService;
import com.project.util.web.enums.Direction;


@SuppressWarnings("all")
public class PrimeDataModel extends LazyDataModel<BaseEntity> {

    BaseService service;
    String method;
    String methodCount;
    Class<? extends BaseEntity> clazz;
    List<BaseEntity> list;

    public PrimeDataModel(){
        super();
    }

    public PrimeDataModel(BaseService service, Class<? extends BaseEntity> clazz, String method, String methodCount){
        super();
        
        this.service = service;
        this.clazz = clazz;
        this.method = method;
        this.methodCount = methodCount;
    }
    
    @Override
    public List<BaseEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {

        if (service != null && method != null && clazz != null) {

            try {

                int start = first;
                int end = first + pageSize;

                Order order = generateOrder(sortField, sortOrder);
                HashMap<String, String> params = generateParameters(filters);
                
                list = (List<BaseEntity>) ReflectionUtil.getObjectByInvokeMethod(service, method, clazz, start, end, params, order);

                setRowCount((Integer) ReflectionUtil.getObjectByInvokeMethod(service, methodCount, clazz, params));
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setPageSize(pageSize);

        return list;
    }
    
    private Order generateOrder(String sortField, SortOrder sortOrder) throws Exception {

        if( sortField != null && !sortField.equalsIgnoreCase("")){
            
            String columnName = getColumnName(sortField);
            Direction direction = Direction.getBySortOrder(sortOrder);
            Order order = new Order(columnName, direction);
            return order;
        }
        return null;
    }

    private HashMap<String, String> generateParameters(Map<String, String> map) throws Exception{
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        if(map != null && !map.isEmpty()){
            
            Iterator it = map.entrySet().iterator();
            
            while (it.hasNext()) {
                
                Map.Entry pairs = (Map.Entry)it.next();

                String key = (String) pairs.getKey();
                
                if(key != null && !key.equalsIgnoreCase("")){
                    
                    String columnName = getColumnName(key);
                    
                    if(columnName != null){
                        
                        String value = (String) pairs.getValue();
                        params.put(columnName, value);
                    }
                }
                
            }
            
        }
        
        return params;
    }
    
    private String getColumnName(String fieldName) throws Exception{
        
        Field field = clazz.getDeclaredField(fieldName);
        
        Transient trans = field.getAnnotation(Transient.class); 
        Column column = field.getAnnotation(Column.class); 
        
        String columnName = null;  
        
        if( column != null && column.name() != null && !column.name().equalsIgnoreCase("")){
            
            columnName = column.name();
        }else if( column != null || trans == null ){
            
            columnName = fieldName;
        }
        
        return columnName;
    }
    
    @Override
    public Object getRowKey(BaseEntity entity) {

        return entity.getId();
    }

    @Override
    public BaseEntity getRowData(String entityId) {

        Integer id = Integer.valueOf(entityId);

        for (BaseEntity entity : list) {

            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    public BaseService getService() {

        return service;
    }

    public void setService(BaseService service) {

        this.service = service;
    }

    public String getMethod() {

        return method;
    }

    public void setMethod(String method) {

        this.method = method;
    }

    public String getMethodCount() {

        return methodCount;
    }

    public void setMethodCount(String methodCount) {

        this.methodCount = methodCount;
    }

    public Class<? extends BaseEntity> getClazz() {

        return clazz;
    }

    public void setClazz(Class<? extends BaseEntity> clazz) {

        this.clazz = clazz;
    }

}
