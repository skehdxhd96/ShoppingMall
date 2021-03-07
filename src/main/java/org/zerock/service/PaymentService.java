package org.zerock.service;

import org.zerock.domain.paymentVO;

public interface PaymentService {

	public int createPayment(paymentVO pv);
	public Integer getTotalPaymentPrice(int orderCode);
	public paymentVO getPaymentInfo(int orderCode);
}
