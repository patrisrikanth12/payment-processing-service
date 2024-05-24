package com.cpt.payments.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cpt.payments.constants.Endpoints;
import com.cpt.payments.dtos.Transaction;
import com.cpt.payments.pojos.PaymentResponse;
import com.cpt.payments.pojos.ProcessingServiceRequest;
import com.cpt.payments.pojos.TransactionReqRes;
import com.cpt.payments.services.PaymentProcessingService;
import com.cpt.payments.services.PaymentStatusService;
import com.cpt.payments.utils.TransactionMapper;

@RestController
@RequestMapping(Endpoints.PAYMENT)
public class PaymentController {
	
	@Autowired
	private PaymentStatusService paymentStatusService;
	
	@Autowired
	private PaymentProcessingService paymentProcessingService;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@PostMapping(Endpoints.STATUS_UPDATE)
	public ResponseEntity<TransactionReqRes> createPayment(@RequestBody TransactionReqRes transactionReq) {
		Transaction transaction = transactionMapper.toDTO(transactionReq);
		System.out.println(transactionReq.getTxnStatusId());
		Transaction returnedTransaction = paymentStatusService.updateStatus(transaction);
		TransactionReqRes returnedTransactionReqRes = transactionMapper.toResponseObject(returnedTransaction);
		return new ResponseEntity<TransactionReqRes>(returnedTransactionReqRes, HttpStatus.CREATED);
	}
	
	@PostMapping(Endpoints.PROCESS_PAYMENT)
	public ResponseEntity<PaymentResponse> processPayment(@RequestBody ProcessingServiceRequest processingServiceRequest) {
		System.out.println("invoked");
		PaymentResponse paymentResponse = this.paymentProcessingService.processPayment(processingServiceRequest);
		return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
	}
}
