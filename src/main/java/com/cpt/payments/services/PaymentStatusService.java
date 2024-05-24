package com.cpt.payments.services;

import com.cpt.payments.dtos.Transaction;

public interface PaymentStatusService {
	public Transaction updateStatus(Transaction transaction);
}
