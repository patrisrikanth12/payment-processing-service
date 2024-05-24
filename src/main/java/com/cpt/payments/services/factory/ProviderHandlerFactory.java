package com.cpt.payments.services.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ProviderHandlerEnum;
import com.cpt.payments.services.ProviderHandler;
import com.cpt.payments.services.provider.handler.StripeProviderHandler;

import io.micrometer.observation.Observation.Context;

@Component
public class ProviderHandlerFactory {
	
	@Autowired
	private ApplicationContext context;
	
	public ProviderHandler getProviderHandler(ProviderHandlerEnum providerHandlerEnum) {
		switch(providerHandlerEnum) {
			case STRIPE: 
				return context.getBean(StripeProviderHandler.class);
			default: return null;
		}
		
	}
}
