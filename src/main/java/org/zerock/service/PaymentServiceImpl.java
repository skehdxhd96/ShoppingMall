package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.paymentVO;
import org.zerock.mapper.PaymentMapper;

import lombok.Setter;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Setter(onMethod_ = @Autowired)
	private PaymentMapper pm;
	
	@Override
	public int createPayment(paymentVO pv) {
		
		return pm.createPayment(pv);
	}
}
