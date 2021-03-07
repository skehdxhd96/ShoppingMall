package org.zerock.mapper;

import org.zerock.domain.paymentVO;

public interface PaymentMapper {

	public int createPayment(paymentVO pv);
	
	public Integer getTotalPaymentPrice(int orderCode);
	public paymentVO getPaymentInfo(int orderCode);
}
