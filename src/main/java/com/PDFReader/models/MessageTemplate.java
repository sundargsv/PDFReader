package com.PDFReader.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "request")
@Component
public class MessageTemplate {

	private String contextTag;
	private String message;
	public String getContextTag() {
		return contextTag;
	}
	public void setContextTag(String contextTag) {
		this.contextTag = contextTag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageTemplate [contextTag=" + contextTag + ", message=" + message + "]";
	}
	
	
	
	
	
}
