package com.example.customer.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail handleResourceNotFound() {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Resource not found");
	}

	@ExceptionHandler(ExternalServiceException.class)
	public ProblemDetail handleExternalServiceFailure() {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, "External service unavailable");
	}
}