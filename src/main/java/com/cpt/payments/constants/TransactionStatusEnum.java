package com.cpt.payments.constants;

import lombok.Getter;

public enum TransactionStatusEnum {
	CREATED(1, "CREATED"),
	PENDING(2, "PENDING"),
	FAILED(3, "FAILED"),
	APPROVED(4, "APPROVED");
	
	@Getter
	private int id;

	@Getter
	private String name;
	
	private TransactionStatusEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static TransactionStatusEnum getTransactionStatusEnum(int transactionStatusId) {
		for (TransactionStatusEnum e : TransactionStatusEnum.values()) {
			if (transactionStatusId == (e.id))
				return e;
		}
		return null;
	}
}
