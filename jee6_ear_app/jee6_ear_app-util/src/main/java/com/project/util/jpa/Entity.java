package com.project.util.jpa;

import java.io.Serializable;
import java.util.List;

import com.project.util.i18n.Message;


public interface Entity<ID extends Number> extends Serializable {

    public ID getId();

    public void setId(ID id);

    public void addMessage(Message message);

    public List<Message> getMessages();

    public void setMessages(List<Message> messages);

    public void clearMessages();

    public String[] getLazyProperties();

    public void setLazyProperties(String[] lazyProperties);

    public Entity getParentEntity();

    public void setParentEntity(Entity parent);

}
