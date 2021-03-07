package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.paymentVO;
import org.zerock.mapper.PaymentMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class PaymentController {

	@Setter(onMethod_ = @Autowired)
	private PaymentMapper pm;
	
	@PostMapping(value = "/payment",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> createPayment(@RequestBody paymentVO pv) {
		
		int createPayment = pm.createPayment(pv);
	
		return createPayment == 1 ? new ResponseEntity<>("Payment Success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
