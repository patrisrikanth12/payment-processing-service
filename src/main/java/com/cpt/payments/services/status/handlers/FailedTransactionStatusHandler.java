package com.cpt.payments.services.status.handlers;

import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.services.TransactionStatusHandler;

public class FailedTransactionStatusHandler implements TransactionStatusHandler{

	@Override
	public boolean updateStatus(Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

}
