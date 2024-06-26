package com.cpt.payments.services.status.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.dtos.TransactionLog;
import com.cpt.payments.services.TransactionStatusHandler;

@Component
public class PendingTransactionStatusHandler implements TransactionStatusHandler{


	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	TransactionLogDao transactionLogDao;
	
	@Override
	public boolean updateStatus(Transaction transaction) {
		int fromTransactionStatusId = transactionDao.getTransactionById(transaction.getId()).getTxnStatusId();
		String fromTransactionStatus = TransactionStatusEnum.getTransactionStatusEnum(fromTransactionStatusId).getName();
		
		boolean transactionStatus = transactionDao.updateTransaction(transaction);
		if (!transactionStatus) {
			System.out.println("Transaction Status Update Failed");
			return false;
		}
		TransactionLog transactionLog = TransactionLog.builder().transactionId(transaction.getId())
				.txnFromStatus(fromTransactionStatus)
				.txnToStatus(TransactionStatusEnum.PENDING.getName()).build();
		transactionLogDao.createTransactionLog(transactionLog);
		return true;
	}


}
