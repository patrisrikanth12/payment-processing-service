package com.cpt.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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


		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(transaction);
        
        int isRowInserted = 0;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
        	isRowInserted = jdbcTemplate.update(sql, parameterSource, keyHolder);
        } catch(Exception e) {
        	System.out.println(e);
        	throw new RuntimeException("Something went wront while inserting transaction");
        }
        transaction.setId(keyHolder.getKey().intValue());        
		return isRowInserted == 0 ? null : transaction;
	}
	
}
