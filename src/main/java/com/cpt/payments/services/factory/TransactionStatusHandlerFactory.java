package com.cpt.payments.services.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.services.TransactionStatusHandler;
import com.cpt.payments.services.status.handlers.ApprovedTransactionStatusHandler;
import com.cpt.payments.services.status.handlers.CreatedTransactionStatusHandler;
import com.cpt.payments.services.status.handlers.FailedTransactionStatusHandler;
import com.cpt.payments.services.status.handlers.PendingTransactionStatusHandler;

@Component
public class TransactionStatusHandlerFactory {
	@Autowired
	private ApplicationContext context;

	public TransactionStatusHandler getStatusFactory(TransactionStatusEnum transactionStatusEnum) {
		switch(transactionStatusEnum) {
		case CREATED:
			return context.getBean(CreatedTransactionStatusHandler.class);
		case APPROVED:
			return context.getBean(ApprovedTransactionStatusHandler.class);
		case FAILED:	
			return context.getBean(FailedTransactionStatusHandler.class);
		case PENDING:	
			return context.getBean(PendingTransactionStatusHandler.class);
		default:
			return null;
		}
	}
}
