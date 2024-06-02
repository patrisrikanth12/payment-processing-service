package com.cpt.payments.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCodeEnum {
	GENERIC_EXCEPTION("20001", "Something went wrong, please try again"),
	TRANSACTION_CREATION_FAILED("20002", "Failed to create the transaction"),
	TRANSACTION_NOT_FOUND("20003", "Transaction with given id not found"),
	PROVIDER_NOT_FOUND("20004", "Provider with given id not found"),
	STRIPE_PROVIDER_ERROR("20005", "Something wrong with Stripe provider");
	
	private String errorCode;
	private String errorMessage;
	
	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
