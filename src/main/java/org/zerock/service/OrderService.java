package org.zerock.service;

import java.util.HashMap;

public interface OrderService {
	public int createOrder(int totalPrice, long customerCode);
	//delivery 인터셉터에서 구매자일 때 로그인된 사용자의 배달코드인지 알아보기 위한 쿼리문
	public long getCustomerCodeByDeliery(int deliveryCode);
	//배송지 입력이 완료됐을 때(배송테이블의 delivery_status가 preparing으로 바꼈을 때) 주문테이블의 order_status를 done으로 바꿈.
	public int updateStatus(int orderCode);
	public long getCustomerCodeByOrder(int orderCode);
}
