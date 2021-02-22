package org.zerock.service;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryService {
	public int createDelivery(DeliveryVO delivery);
	public HashMap<String, Object> getDelivery(int deliveryCode);
	//배송페이지 입력 후 배송테이블 업데이트하기
	public HashMap<String, Object> orderSuccess(DeliveryVO deliveryVO);
	public DeliveryVO getDeliveryByOrderCode(int orderCode);
	public int updateDeliveryStatus(int orderCode, String deliveryStatus);
	public int updateDeliveryInfo(DeliveryVO delivery);
}
