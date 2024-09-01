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

@RestController
@RequestMapping(Endpoints.PAYMENT)
public class PaymentController {
	
	@Autowired
	private PaymentStatusService paymentStatusService;
	
	@Autowired
	private PaymentProcessingService paymentProcessingService;

	private ModelMapper modelMapper;
	
	@PostMapping(Endpoints.CREATE_PAYMENT)
	public ResponseEntity<TransactionReqRes> createPayment(@RequestBody TransactionReqRes transactionReq) {
		Transaction transaction = modelMapper.map(transactionReq, Transaction.class);
		Transaction returnedTransaction = this.paymentStatusService.updateStatus(transaction);
		TransactionReqRes returnedTransactionReqRes = this.modelMapper.map(returnedTransaction, TransactionReqRes.class);
		return new ResponseEntity<TransactionReqRes>(returnedTransactionReqRes, HttpStatus.CREATED);
	}
	
	@PostMapping(Endpoints.PROCESS_PAYMENT)
	public ResponseEntity<PaymentResponse> processPayment(@RequestBody ProcessingServiceRequest processingServiceRequest) {
		PaymentResponse paymentResponse = this.paymentProcessingService.processPayment(processingServiceRequest);
		return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
	}
}
