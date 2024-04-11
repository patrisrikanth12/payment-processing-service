package com.cpt.payments.dao;

import com.cpt.payments.dtos.TransactionLog;

public interface TransactionLogDao {
	public boolean createTransactionLog(TransactionLog transactionLog);
}
