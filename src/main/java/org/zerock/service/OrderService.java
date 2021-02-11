package org.zerock.service;

public interface OrderService {
	public int createOrder(int totalPrice, long customerCode);
	//delivery 인터셉터에서 구매자일 때 로그인된 사용자의 배달코드인지 알아보기 위한 쿼리문
	public long getCustomerCode(int deliveryCode);
}
