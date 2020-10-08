package com.project.exception;

public class InvalidQuantityException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	  public String errorField = null;

	  public InvalidQuantityException(String errorField) {
	    this.errorField = errorField;
	  }
}
