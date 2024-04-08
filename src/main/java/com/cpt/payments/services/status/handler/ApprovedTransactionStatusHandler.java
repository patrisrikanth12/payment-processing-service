package com.cpt.payments.services.status.handler;

import org.springframework.stereotype.Component;

import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.services.TransactionStatusHandler;

@Component
public class ApprovedTransactionStatusHandler implements TransactionStatusHandler{

	@Override
	public boolean updateStatus(Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
