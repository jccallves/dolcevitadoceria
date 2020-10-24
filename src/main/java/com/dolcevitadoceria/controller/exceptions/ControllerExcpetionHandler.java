package com.dolcevitadoceria.controller.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dolcevitadoceria.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExcpetionHandler {

		@ExceptionHandler(ObjectNotFoundException.class)
		public ResponseEntity<StandardError> objNotFound(ObjectNotFoundException e, HttpServletRequest request){
			
			StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
}