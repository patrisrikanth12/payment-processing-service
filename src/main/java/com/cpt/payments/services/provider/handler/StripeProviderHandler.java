package com.cpt.payments.services.provider.handler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpRestTemplateEngine;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;
import com.cpt.payments.pojos.ProviderServiceErrorResponse;
import com.cpt.payments.pojos.StripeProviderRequest;
import com.cpt.payments.pojos.StripeProviderResponse;
import com.cpt.payments.services.ProviderHandler;
import com.google.gson.Gson;

@Component
public class StripeProviderHandler implements ProviderHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(StripeProviderHandler.class);
	
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
		
		ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);
		if(response == null || response.getBody() == null) {
			LOGGER.debug("Response | response body is null");
			throw new PaymentProcessingException(
					HttpStatus.INTERNAL_SERVER_ERROR, 
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), 
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
		
		if(!response.getStatusCode().is2xxSuccessful()) {
			handleNon200Response(response);
		}
												
		StripeProviderResponse stripeProviderResponse = gson.fromJson(response.getBody(), StripeProviderResponse.class);
		PaymentResponse paymentResponse = PaymentResponse.builder()
				.paymentReference(transaction.getTxnReference())
				.redirectUrl(stripeProviderResponse.getRedirectUrl())
				.build();
		return paymentResponse;
	}
	
	public void handleNon200Response(ResponseEntity<String> response) {
		ProviderServiceErrorResponse providerServiceErrorResponse = gson.fromJson(response.getBody(), ProviderServiceErrorResponse.class);
		
		if(providerServiceErrorResponse.isTpProviderError()) {
			LOGGER.debug("Stripe third party error || " + providerServiceErrorResponse);
			throw new PaymentProcessingException(
					HttpStatus.INTERNAL_SERVER_ERROR, 
					providerServiceErrorResponse.getErrorCode(), 
					providerServiceErrorResponse.getErrorMessage()
					);
		}
		
		LOGGER.debug("Error in Stripe Provider Service " + providerServiceErrorResponse);
		throw new PaymentProcessingException(
				HttpStatus.INTERNAL_SERVER_ERROR, 
				ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), 
				ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage()
				);
	}
}