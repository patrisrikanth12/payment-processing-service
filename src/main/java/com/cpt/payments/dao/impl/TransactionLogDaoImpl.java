package com.cpt.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.dtos.TransactionLog;

@Component
public class TransactionLogDaoImpl implements TransactionLogDao{
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public boolean createTransactionLog(TransactionLog log) {
		String sql = "INSERT INTO transaction_log (transactionId, txnFromStatus, txnToStatus) "
                + "VALUES (:transactionId, :txnFromStatus, :txnToStatus)";
		Map<String, Object> params = new HashMap<>();
        params.put("transactionId", log.getTransactionId());
        params.put("txnFromStatus", log.getTxnFromStatus());
        params.put("txnToStatus", log.getTxnToStatus());

        try {
        	jdbcTemplate.update(sql, params);
        	return true;
        } catch(Exception e) {
        	System.out.println("Unable to log the transaction " + e.getMessage());
        };
		return false;
	}
}
