package com.dolcevitadoceria.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001037406561432138L;

	public ObjectNotFoundException (String msg) {
		
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
