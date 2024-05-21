package com.cpt.payments.services.provider.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpRestTemplateEngine;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;
import com.cpt.payments.pojos.StripeProviderRequest;
import com.cpt.payments.pojos.StripeProviderResponse;
import com.cpt.payments.services.ProviderHandler;
import com.google.gson.Gson;

@Component
public class StripeProviderHandler implements ProviderHandler {
	
	@Autowired
	private HttpRestTemplateEngine httpRestTemplateEngine;
	
	private Gson gson = new Gson();
	
	@Value("${stripe.provider.service.process.payment}")
	private String paymentUrl;
	
	@Override
	public PaymentResponse processPayment(Transaction transaction, ProcessingServiceRequest processingServiceRequest) {
		StripeProviderRequest stripeProviderRequest = StripeProviderRequest.builder()
															.transactionReference(transaction.getTxnReference())
															.amount(transaction.getAmount())
															.currency(transaction.getCurrency())
															.quantity(processingServiceRequest.getQuantity())
															.successUrl(processingServiceRequest.getSuccessUrl())
															.cancelUrl(processingServiceRequest.getCancelUrl())
															.currency(transaction.getCurrency())
															.productDescription(processingServiceRequest.getProductDescription())
															.build();
		HttpRequest httpRequest = HttpRequest.builder()
										.httpMethod(HttpMethod.POST)
										.request(gson.toJson(stripeProviderRequest))
										.url(paymentUrl)
										.build();
		ResponseEntity<String> responseString = httpRestTemplateEngine.execute(httpRequest);
		StripeProviderResponse stripeProviderResponse = gson.fromJson(responseString.getBody(), StripeProviderResponse.class);
		PaymentResponse paymentResponse = PaymentResponse.builder()
												.paymentReference(transaction.getTxnReference())
												.redirectUrl(stripeProviderResponse.getRedirectUrl())
												.build();
												
		return paymentResponse;
	}
}
