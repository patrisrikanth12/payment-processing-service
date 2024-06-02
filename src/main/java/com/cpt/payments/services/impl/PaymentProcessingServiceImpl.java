package com.cpt.payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.ProviderHandlerEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;
import com.cpt.payments.services.PaymentProcessingService;
import com.cpt.payments.services.ProviderHandler;
import com.cpt.payments.services.factory.ProviderHandlerFactory;

@Component
public class PaymentProcessingServiceImpl implements PaymentProcessingService{
	
	@Autowired
	private ProviderHandlerFactory providerHandlerFactory;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Override
	public PaymentResponse processPayment(ProcessingServiceRequest processingServiceRequest) {
		Transaction transaction = transactionDao.getTransactionById(processingServiceRequest.getTransactionId());
		if(transaction == null) {
			throw new PaymentProcessingException(
					HttpStatus.BAD_REQUEST, 
					ErrorCodeEnum.TRANSACTION_NOT_FOUND.getErrorCode(), 
					ErrorCodeEnum.TRANSACTION_NOT_FOUND.getErrorMessage()
					);
		}
		
		ProviderHandler providerHandler = providerHandlerFactory.getProviderHandler(ProviderHandlerEnum.getProviderEnumById(transaction.getProviderId()));
		if(providerHandler == null) {
			throw new PaymentProcessingException(
					HttpStatus.BAD_REQUEST, 
					ErrorCodeEnum.PROVIDER_NOT_FOUND.getErrorCode(), 
					ErrorCodeEnum.PROVIDER_NOT_FOUND.getErrorMessage()
					);
		}
		
		PaymentResponse paymentResponse = providerHandler.processPayment(transaction, processingServiceRequest);
		return paymentResponse;
	}
}
