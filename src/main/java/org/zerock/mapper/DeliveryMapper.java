package org.zerock.mapper;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryMapper {
	public int createDelivery(DeliveryVO delivery);
	public HashMap<String, Object> getDelivery(int deliveryCode);
	public DeliveryVO getDeliveryByOrderCode(int orderCode);
	public int orderSuccess(DeliveryVO deliveryVO);
	
}
