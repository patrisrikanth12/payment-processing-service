package com.cpt.payments.services;

import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;

public interface ProviderHandler {
	PaymentResponse processPayment(Transaction transaction, ProcessingServiceRequest processingServiceRequest);
}
