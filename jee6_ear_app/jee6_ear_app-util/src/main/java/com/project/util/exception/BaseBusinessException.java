package com.project.util.exception;

import java.util.ArrayList;
import java.util.List;

import com.project.util.i18n.Message;


public class BaseBusinessException extends RuntimeException {
	
	List<Message> messages = new ArrayList<Message>();
	
	public BaseBusinessException() {
		super();
	}
	
	public BaseBusinessException(List<Message> messages, Throwable cause) {
		super(cause);
	}
	
	public BaseBusinessException(Throwable cause) {
		super(cause);
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String messageKey) {
		messages.add(new Message(messageKey));
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

}