package org.zerock.service;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryService {
	public HashMap<String, Object> getDelivery(int deliveryCode);
	public int createDelivery(int deliveryCode, long customerCode);
}
