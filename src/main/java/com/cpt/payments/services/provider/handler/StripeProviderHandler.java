package com.cpt.payments.services.provider.handler;

import org.springframework.stereotype.Component;

import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;
import com.cpt.payments.services.ProviderHandler;

@Component
public class StripeProviderHandler implements ProviderHandler {
	@Override
	public PaymentResponse processPayment(Transaction transaction, ProcessingServiceRequest processingServiceRequest) {
		PaymentResponse paymentResponse = PaymentResponse.builder().redirectUrl("example.com").paymentReference("abcdef").build();
		return paymentResponse;
	}
}
