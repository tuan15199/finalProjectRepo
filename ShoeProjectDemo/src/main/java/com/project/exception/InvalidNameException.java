package com.project.exception;

public class InvalidNameException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	  public String errorField = null;

	  public InvalidNameException(String errorField) {
	    this.errorField = errorField;
	  }
}
