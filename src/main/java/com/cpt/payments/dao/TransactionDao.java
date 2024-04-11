package com.cpt.payments.dao;

import com.cpt.payments.dtos.Transaction;

public interface TransactionDao {
	Transaction createTransaction(Transaction transaction);
	boolean updateTransaction(Transaction transaction);
}
