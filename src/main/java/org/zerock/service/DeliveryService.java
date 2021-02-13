package org.zerock.service;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryService {
	public HashMap<String, Object> getDelivery(int deliveryCode);
	public int createDelivery(int deliveryCode, long customerCode);
	//배송페이지 입력 후 배송테이블 업데이트하기
	public HashMap<String, Object> orderSuccess(DeliveryVO deliveryVO);
}
