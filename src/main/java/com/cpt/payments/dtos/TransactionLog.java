package com.cpt.payments.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {
	private Integer id;
	private Long transactionId;
	private String txnFromStatus;
	private String txnToStatus;
}
