package com.cpt.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dtos.Transaction;

@Component
public class TransactionDaoImpl implements TransactionDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Transaction createTransaction(Transaction transaction) {
		String sql = "INSERT INTO transaction ("
	             + "userId, paymentMethodId, providerId, paymentTypeId, amount, currency, "
	             + "txnStatusId, txnReference, txnDetailsId, providerCode, providerMessage, "
	             + "debitorAccount, creditorAccount, providerReference, merchantTransactionReference, retryCount) "
	             + "VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :amount, :currency, "
	             + ":txnStatusId, :txnReference, :txnDetailsId, :providerCode, :providerMessage, "
	             + ":debitorAccount, :creditorAccount, :providerReference, :merchantTransactionReference, :retryCount)";


        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", transaction.getId());
        namedParameters.put("userId", transaction.getUserId());
        namedParameters.put("paymentMethodId", transaction.getPaymentMethodId());
        namedParameters.put("providerId", transaction.getProviderId());
        namedParameters.put("paymentTypeId", transaction.getPaymentTypeId());
        namedParameters.put("amount", transaction.getAmount());
        namedParameters.put("currency", transaction.getCurrency());
        namedParameters.put("txnStatusId", transaction.getTxnStatusId());
        namedParameters.put("txnReference", transaction.getTxnReference());
        namedParameters.put("txnDetailsId", transaction.getTxnDetailsId());
        namedParameters.put("providerCode", transaction.getProviderCode());
        namedParameters.put("providerMessage", transaction.getProviderMessage());
        namedParameters.put("debitorAccount", transaction.getDebitorAccount());
        namedParameters.put("creditorAccount", transaction.getCreditorAccount());
        namedParameters.put("providerReference", transaction.getProviderReference());
        namedParameters.put("merchantTransactionReference", transaction.getMerchantTransactionReference());
        namedParameters.put("retryCount", transaction.getRetryCount());
        
        int isRowInserted = 0;
        try {
        	isRowInserted = jdbcTemplate.update(sql, namedParameters);
        } catch(Exception e) {
        	System.out.println(e);
        	throw new RuntimeException("Something went wront while inserting transaction");
        }

		return isRowInserted == 0 ? null : transaction;
	}
	
}
