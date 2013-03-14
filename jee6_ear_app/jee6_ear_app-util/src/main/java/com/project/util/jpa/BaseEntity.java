package com.project.util.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.project.util.i18n.Message;


public abstract class BaseEntity<ID extends Number> implements Entity<ID> {

    @Transient
    private List<Message> messages = new ArrayList<Message>();

    private String[] lazyProperties = new String[0];

    public void setParentEntity(Entity parent) {

    }

    public Entity getParentEntity() {

        return null;
    }

    public void setMessages(List<Message> messages) {

        this.messages = messages;
    }

    public List<Message> getMessages() {

        return messages;
    }

    public void addMessage(Message message) {

        messages.add(message);
    }

    public void removeMessage(Message message) {

        messages.remove(message);
    }

    public void clearMessages() {

        messages.clear();
    }

    public String[] getLazyProperties() {

        return lazyProperties;
    }

    public void setLazyProperties(String[] lazyProperties) {

        this.lazyProperties = lazyProperties;
    }
}
