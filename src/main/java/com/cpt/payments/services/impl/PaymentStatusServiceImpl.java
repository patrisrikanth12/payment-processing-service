package com.cpt.payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
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
		boolean isUpdated = transactionStatusHandler.updateStatus(transaction);
		if(!isUpdated) {
			throw new PaymentProcessingException(
					HttpStatus.INTERNAL_SERVER_ERROR, 
					ErrorCodeEnum.TRANSACTION_CREATION_FAILED.getErrorCode(), 
					ErrorCodeEnum.TRANSACTION_CREATION_FAILED.getErrorMessage()
			);
		}
		return transaction;
	}
	
}
