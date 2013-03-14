package com.project.util.i18n;

import java.util.ArrayList;
import java.util.List;


public class Message {
	
	public enum MessageSeverity { INFO, WARN, ERROR };
	
	private String key;
	private MessageSeverity severity;
	private List parameters = new ArrayList();

	public Message(String key) {
		this.key = key;
	}
	
	public Message(String key, MessageSeverity severity) {
		this.key = key;
		this.severity = severity;
	}
	
	public Message(String key, MessageSeverity severity, List parameters) {
		this.key = key;
		this.severity = severity;
		this.parameters = parameters;
	}	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MessageSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(MessageSeverity severity) {
		this.severity = severity;
	}

	public void addParameter(Object param) {
		this.parameters.add(param);
	}
	
	public List getParameters() {
		return parameters;
	}

	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Message)) {
			return false;
		}
		Message message = (Message) obj;
		return this.key.equals(message.getKey());
	}

}