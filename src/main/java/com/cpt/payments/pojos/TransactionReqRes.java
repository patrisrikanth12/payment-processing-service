package com.cpt.payments.pojos;

public class TransactionReqRes {
	private Integer id;
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
}
