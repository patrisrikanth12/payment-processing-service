package com.cpt.payments.dao;

import com.cpt.payments.dtos.TransactionLog;

public interface TransactionLogDao {
	public TransactionLog createTransactionLog(TransactionLog transactionLog);
}
