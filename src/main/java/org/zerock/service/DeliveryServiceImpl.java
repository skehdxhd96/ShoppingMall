package org.zerock.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.DeliveryVO;
import org.zerock.mapper.DeliveryMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class DeliveryServiceImpl implements DeliveryService {
	@Resource
	DeliveryMapper deliveryMapper;

	@Override
	public HashMap<String, Object> getDelivery(int deliveryCode) {
		log.info("getDelivery() : " + deliveryCode);
		
		return deliveryMapper.getDelivery(deliveryCode);
	}

	@Override
	public HashMap<String, Object> orderSuccess(DeliveryVO deliveryVO) {
		HashMap<String, Object> resultHm = new HashMap<String, Object>();	//������ �ؽø� �ν��Ͻ�
		int result = deliveryMapper.orderSuccess(deliveryVO);	//������Ʈ ���
		resultHm.put("result", result);
		
		if (result!=0) {
			resultHm.put("orderCode", deliveryVO.getOrderCode());	//������Ʈ ���� �� ��ȯ�� orderCode
		}
		
		log.info("DeliveryServiceImpl orderSuccess result:"+resultHm.toString());
		
		return resultHm;
	}

	@Override
	public int createDelivery(DeliveryVO delivery) {
		
		return deliveryMapper.createDelivery(delivery);
	}

	@Override
	public DeliveryVO getDeliveryByOrderCode(int orderCode) {
		DeliveryVO delivery = deliveryMapper.getDeliveryByOrderCode(orderCode);
		
		return delivery;
	}

	@Override
	public int updateDeliveryStatus(int orderCode, String deliveryStatus) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orderCode", orderCode);
		hm.put("deliveryStatus", deliveryStatus);
		
		return deliveryMapper.updateDeliveryStatus(hm);
	}

	@Override
	public int updateDeliveryInfo(HashMap<String, Object> deliveryHm) {
		DeliveryVO delivery = new DeliveryVO(Integer.parseInt(deliveryHm.get("deliveryZipCode").toString()), deliveryHm.get("shippingAddress").toString(), deliveryHm.get("requests").toString(), 
				deliveryHm.get("deliverPhone").toString(), Integer.parseInt(deliveryHm.get("orderCode").toString()), deliveryHm.get("recipient").toString());
		
		return deliveryMapper.updateDeliveryInfo(delivery);
	}

}
