package com.project.exception;

public class DataAlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public String errorField = null;

	public DataAlreadyExistException(String errorField) {
		this.errorField = errorField;
	}
}
