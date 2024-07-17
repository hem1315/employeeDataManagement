package com.edm.exception;

public class ItemNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(Long id) {
		 super("Entry not found with ID: " + id);
		 }

}
