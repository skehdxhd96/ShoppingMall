package org.zerock.service;

import java.util.HashMap;

import org.zerock.domain.DeliveryVO;

public interface DeliveryService {
	public int createDelivery(DeliveryVO delivery);
	public HashMap<String, Object> getDelivery(int deliveryCode);
	//��������� �Է� �� ������̺� ������Ʈ�ϱ�
	public HashMap<String, Object> orderSuccess(DeliveryVO deliveryVO);
	public DeliveryVO getDeliveryByOrderCode(int orderCode);
	public int updateDeliveryStatus(int orderCode, String deliveryStatus);
	public int updateDeliveryInfo(HashMap<String, Object> deliveryHm);
}
