package org.zerock.mapper;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryMapper {
	public HashMap<String, Object> getDelivery(int deliveryCode);
	public int createDelivery(HashMap<String, Object> parameterHm);
	public int orderSuccess(DeliveryVO deliveryVO);
}
