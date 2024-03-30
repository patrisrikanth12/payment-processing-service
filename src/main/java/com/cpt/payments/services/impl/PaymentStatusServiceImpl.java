package com.cpt.payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.services.PaymentStatusService;
import com.cpt.payments.services.TransactionStatusHandler;
import com.cpt.payments.services.factory.TransactionStatusHandlerFactory;

public class PaymentStatusServiceImpl implements PaymentStatusService{
	
	@Autowired
	private TransactionStatusHandlerFactory transactionStatusHandlerFactory;

	@Override
	public Transaction updateStatus(Transaction transaction) {
		TransactionStatusEnum transactionStatusEnum = TransactionStatusEnum.getTransactionStatusEnum(transaction.getTxnStatusId());
		TransactionStatusHandler transactionStatusHandler = transactionStatusHandlerFactory.getStatusFactory(transactionStatusEnum);
		transactionStatusHandler.updateStatus(transaction);
		return transaction;
	}
	
}
