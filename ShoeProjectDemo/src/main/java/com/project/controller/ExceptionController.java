package com.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.exception.DataAlreadyExistException;
import com.project.exception.DataNotFoundException;
import com.project.exception.InvalidNameException;
import com.project.exception.InvalidQuantityException;
import com.project.utils.ErrorResponse;

@ControllerAdvice
public class ExceptionController {
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(value = DataAlreadyExistException.class)
	public ResponseEntity<Object> exception(DataAlreadyExistException exception) {
		logger.error(exception.errorField + " is already exist");
		return ErrorResponse.getFormattedError(exception.errorField + " is already exist", HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = InvalidNameException.class)
	public ResponseEntity<Object> exception(InvalidNameException exception) {
		logger.error(exception.errorField + " is invalid, it cannot be a empty string or null");
		return ErrorResponse.getFormattedError(
				exception.errorField + " is invalid, it cannot be a empty string or null", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<Object> exception(DataNotFoundException exception) {
		logger.error(exception.errorField + " not found");
		return ErrorResponse.getFormattedError(exception.errorField + " not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = InvalidQuantityException.class)
	public ResponseEntity<Object> exception(InvalidQuantityException exception) {
		logger.error(exception.errorField + " cannot be null or less than 0");
		return ErrorResponse.getFormattedError(exception.errorField + " cannot be null or less than 0",
				HttpStatus.FORBIDDEN);
	}
}
