package com.example.microservice.camel.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.microservice.exception.ApiError;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@RestControllerAdvice
@Slf4j
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Request Error", ex);
		String error = "Malformed JSON request";
		ApiError apiError = new ApiError(status, error, ex);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("Request Error", ex);
		// TODO
		String error = "bad request";
		// return buildResponseEntity(new ApiError(status, error, ex));
		return new ResponseEntity<>(new ApiError(status, error, ex), status);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleInternalServerException(Exception ex, HttpServletRequest request) {
		log.error("Internal Error", ex);
		// TODO
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiError.builder().setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(ex.getMessage()));
	}

}