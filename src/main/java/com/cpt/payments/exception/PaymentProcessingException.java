package com.cpt.payments.exception;

import org.springframework.http.HttpStatusCode;

public class PaymentProcessingException extends RuntimeException {
	private HttpStatusCode httpStatusCode;
	private String errorCode;
	private String errorMessage;
	
	public PaymentProcessingException(HttpStatusCode httpStatusCode, String errorCode, String errorMessage) {
		this.httpStatusCode = httpStatusCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public HttpStatusCode getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
