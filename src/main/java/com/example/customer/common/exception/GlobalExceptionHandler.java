package com.example.customer.common.exception;

import org.jspecify.annotations.NonNull;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request
	) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, "Request validation failed");
		Map<String, String> errors = new LinkedHashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error ->
			errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
		problem.setProperty("errors", errors);
		return handleExceptionInternal(exception, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException exception, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request
	) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
			status, "Request body is invalid");
		return handleExceptionInternal(exception, problem, headers, status, request);
	}
}