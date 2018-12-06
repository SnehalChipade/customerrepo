package com.snehal.entity.model;

/**
 * @author $nehal
 *
 */
public class Message {
	
	String message;

	public Message(){}
	
	public Message(String msg){
		
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
