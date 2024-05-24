package com.cpt.payments.services.status.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.dtos.TransactionLog;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.services.TransactionStatusHandler;

@Component
public class CreatedTransactionStatusHandler implements TransactionStatusHandler{
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private TransactionLogDao transactionLogDao;

	@Override
	public boolean updateStatus(Transaction transaction) {
		transaction.setTxnDetailsId(TransactionStatusEnum.CREATED.getId());
		Transaction txResponse = transactionDao.createTransaction(transaction);
		if(txResponse == null) {
			System.out.println("Failed to insert transaction");
			return false;
		}
		TransactionLog tnxLog = TransactionLog.builder()
										.transactionId(txResponse.getId())
										.txnFromStatus("-")
										.txnToStatus(TransactionStatusEnum.CREATED.getName())
										.build();
															
		transactionLogDao.createTransactionLog(tnxLog);
		return true;
	}

}
