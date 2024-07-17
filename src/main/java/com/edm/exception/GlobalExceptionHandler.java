package com.edm.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ItemNotFoundException.class)
	public String handleItemNotFoundException(ItemNotFoundException ex) {
		return "error";
	}
}
