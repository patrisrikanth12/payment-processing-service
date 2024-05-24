package com.cpt.payments.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.pojos.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(PaymentProcessingException.class)
	public ResponseEntity<ErrorResponse> handleProcessingServiceException(PaymentProcessingException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(e.getErrorCode());
		errorResponse.setErrorMessage(e.getErrorMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, e.getHttpStatusCode());
	}
}
