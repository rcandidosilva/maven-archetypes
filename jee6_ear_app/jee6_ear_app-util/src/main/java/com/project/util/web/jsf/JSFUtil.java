package com.project.util.web.jsf;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.PersistenceException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.project.util.annotation.BaseQuery;
import com.project.util.exception.BaseSystemException;
import com.project.util.i18n.Message;
import com.project.util.i18n.Message.MessageSeverity;
import com.project.util.jpa.Entity;


public class JSFUtil {
	
	public static final String GLOBAL_MESSAGES_REQUEST_KEY = "globalMessages";
	public static final String MESSAGES_BUNDLE_NAME = "messages";
	
	public static String getBundledMessage(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		//String bundleName = context.getApplication().getMessageBundle();
		// TODO: Ajustar o carregamento do resource bundle para ficar generico
		try {
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, MESSAGES_BUNDLE_NAME);
			return bundle.getString(key);
		} catch (Exception ex) {
			return key;
		}
	}

	public static String getBundledMessage(Message message) {
		try {
			String pattern = getBundledMessage(message.getKey());
			return MessageFormat.format(pattern, message.getParameters().toArray());
		} catch (Exception ex) {
			return message.getKey();
		}
	}
	
	public static void addComponentInfoMessage(String componentId, String message) {
		String summary = getBundledMessage(message);
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		addComponentMessage(componentId, facesMessage);
	}
	
	public static void addComponentMessage(String componentId, FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(componentId, message);		
	}
	
	public static void addGlobalMessage(FacesMessage message) {
		ServletRequest request = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute(GLOBAL_MESSAGES_REQUEST_KEY, "true");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void addGlobalErrorMessage(Message message, Exception ex) {
		String detail = ex == null ? null : ex.getMessage();
		FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundledMessage(message), detail);
		addGlobalMessage(errorMessage);
	}
	
	public static void addGlobalInfoMessage(String message) {
		FacesMessage infoMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
				getBundledMessage(new Message(message)), null);
		addGlobalMessage(infoMessage);
	}	

	public static void addGlobalInfoMessage(Message message) {
		FacesMessage infoMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
				getBundledMessage(message), null);
		addGlobalMessage(infoMessage);
	}
	
	public static void addGlobalWarningMessage(String message) {
		FacesMessage warnMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
				getBundledMessage(new Message(message)), null);
		addGlobalMessage(warnMessage);
	}

	public static void addGlobalWarningMessage(Message message) {
		FacesMessage warnMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
				getBundledMessage(message), null);
		addGlobalMessage(warnMessage);
	}	

	public static void handleException(Exception ex) {
		Throwable cause =(ex.getCause());
		
		/*
		 * 
		if (cause instanceof BaseBusinessException) {
			List<Message> messages = ((BaseBusinessException) cause).getMessages();
			addMessages(messages);
		} else
		 */
		if (cause instanceof BaseSystemException) {
			// TODO: Coloca uma mensage gen�rica de erro
		} else if (cause instanceof PersistenceException) {
			addGlobalErrorMessage(new Message("saaf.global.msg.error.constraint"), ex); 
		} else {
			addGlobalErrorMessage(new Message("saaf.global.msg.error.inesperado"), ex);
		}
	}
	
	public static void generateMessages(Entity entity) {
		List<Message> messages = entity.getMessages();
		addMessages(messages);
		entity.clearMessages();
	}
	
	public static void generateMessages(Entity entity, String defaultMessage) {
		List<Message> messages = entity.getMessages();
		if (messages == null || messages.isEmpty()) {
			addGlobalInfoMessage(defaultMessage);
		} else {
			addMessages(messages);
			entity.clearMessages();
		}
	}
	
	public static void addMessages(Collection<Message> messages) {
		for (Message message : messages) {
			if (MessageSeverity.INFO.equals(message.getSeverity())) {
				addGlobalInfoMessage(message);
			} else if (MessageSeverity.WARN.equals(message.getSeverity())) {
				addGlobalWarningMessage(message);
			} else if (MessageSeverity.ERROR.equals(message.getSeverity())) {
				addGlobalErrorMessage(message, null);
			} else {
				addGlobalErrorMessage(message, null);
			}
		}
	}
	
	/**
	 * @param list
	 * @return
	 */
	public static List<SelectItem> getListItems(List<Entity> list) {
		List<SelectItem> newList = new ArrayList<SelectItem>();
		for (Entity entity : list) {
			newList.add(new SelectItem(entity.getId(), entity.toString()));
		}
		return newList;
	}	
	
    public static List<SelectItem> getListItems(List<Entity> listEntity, BaseQuery query) throws Exception {
    	List<SelectItem> result = new ArrayList<SelectItem>();
    	for (Entity entity : listEntity) {    		
    		Object label = PropertyUtils.getProperty(entity, query.label());
    		Object value = PropertyUtils.getProperty(entity, query.value());
    		result.add(new SelectItem(value, String.valueOf(label)));
    	}
    	return result;
    }	
	
	public static String getRequestParameter(String name) {
		return getServletRequest().getParameter(name);
	}
	
	public static HttpServletRequest getServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
	public static HttpSession getSession() {
		return getServletRequest().getSession();
	}
	
}