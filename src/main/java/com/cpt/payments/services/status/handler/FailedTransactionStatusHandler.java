package com.cpt.payments.services.status.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.dtos.TransactionLog;
import com.cpt.payments.services.TransactionStatusHandler;

@Component
public class FailedTransactionStatusHandler implements TransactionStatusHandler{
	
	private final Logger logger = LogManager.getLogger(FailedTransactionStatusHandler.class);
	
	@Autowired
	private TransactionLogDao transactionLogDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Override
	public boolean updateStatus(Transaction transaction) {
		int fromTransactionStatusId = transactionDao.getTransactionById(transaction.getId()).getTxnStatusId();
		String fromTransactionStatus = TransactionStatusEnum.getTransactionStatusEnum(fromTransactionStatusId).getName();
		
		boolean transactionUpdate = transactionDao.updateTransaction(transaction);
		if(!transactionUpdate) {
				System.out.println("Transaction Status Update Failed");
				return false;
		}
		
		TransactionLog transactionLog = TransactionLog.builder()
				.transactionId(transaction.getId())
				.txnFromStatus(fromTransactionStatus)
				.txnToStatus(TransactionStatusEnum.FAILED.getName())
				.build();
		try {
			transactionLogDao.createTransactionLog(transactionLog);
			return true;
		} catch(Exception e) {
			logger.debug("failed to create transaction log " + e.getMessage());
		}
		
		return false;
	}

}
