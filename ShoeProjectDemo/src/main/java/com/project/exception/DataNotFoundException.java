package com.project.exception;

public class DataNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	  public String errorField = null;

	  public DataNotFoundException(String errorField) {
	    this.errorField = errorField;
	  }
}
