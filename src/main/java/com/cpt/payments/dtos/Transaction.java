package com.cpt.payments.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	private Long id;
	private long userId;
	private Integer paymentMethodId;
	private Integer providerId;
	private Integer paymentTypeId;
	private double amount;
	private String currency;
	private Integer txnStatusId;
	private String txnReference;
	private Integer txnDetailsId;
	private String providerCode;
	private String providerMessage;
	private String debitorAccount;
	private String creditorAccount;
	private String providerReference;
	private String merchantTransactionReference;
	private int retryCount;
}
