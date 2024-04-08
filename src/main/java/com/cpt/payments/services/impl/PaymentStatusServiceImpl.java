package com.cpt.payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.services.PaymentStatusService;
import com.cpt.payments.services.TransactionStatusHandler;
import com.cpt.payments.services.factory.TransactionStatusHandlerFactory;

@Component
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
