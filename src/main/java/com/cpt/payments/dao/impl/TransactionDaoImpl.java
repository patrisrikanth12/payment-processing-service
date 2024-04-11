package com.cpt.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;

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
			transaction.setId(keyHolder.getKey().intValue());
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the transaction");
		}
		return isRowInserted == 0 ? null : transaction;
	}

	public boolean updateTransaction(Transaction transaction) {
		String sql = "UPDATE transaction SET txnStatusId = :txnStatusId WHERE id = :id";
		Map<String, Object> params = new HashMap<>();
		params.put("txnStatusId", transaction.getTxnStatusId());
		params.put("id", transaction.getId());
		try {
			jdbcTemplate.update(sql, params);
			return true;
		} catch(Exception e) {
			System.out.println("Transaction Status Update Failed");
		}
		return false;
	}
}
