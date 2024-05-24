package com.cpt.payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ProviderHandlerEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dtos.Transaction;
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
			// throw exception
		}
		
		ProviderHandler providerHandler = providerHandlerFactory.getProviderHandler(ProviderHandlerEnum.getProviderEnumById(transaction.getProviderId()));
		if(providerHandler == null) {
			// throw Exception
		}
		
		PaymentResponse paymentResponse = providerHandler.processPayment(transaction, processingServiceRequest);
		return paymentResponse;
	}
}
