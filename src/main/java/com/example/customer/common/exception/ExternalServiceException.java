package com.example.customer.common.exception;

public class ExternalServiceException extends RuntimeException {

	public ExternalServiceException() {

		super("External service unavailable");
	}
}
