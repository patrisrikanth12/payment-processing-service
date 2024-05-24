package com.cpt.payments.services;

import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;

public interface PaymentProcessingService {
	PaymentResponse processPayment(ProcessingServiceRequest processingServiceRequest);
}
