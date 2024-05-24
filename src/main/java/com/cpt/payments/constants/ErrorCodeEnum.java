package com.cpt.payments.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCodeEnum {
	GENERIC_EXCEPTION(20001, "Something went wrong, please try again"),
	TRANSACTION_CREATION_FAILED(20002, "Failed to create the transaction");
	
	private int errorCode;
	private String errorMessage;
	
	ErrorCodeEnum(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
