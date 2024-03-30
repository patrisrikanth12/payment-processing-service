package com.cpt.payments.services;

import com.cpt.payments.dtos.Transaction;

public interface TransactionStatusHandler {
	public boolean updateStatus(Transaction transaction);
}
