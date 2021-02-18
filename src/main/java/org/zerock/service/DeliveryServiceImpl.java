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
		HashMap<String, Object> resultHm = new HashMap<String, Object>();	//리턴할 해시맵 인스턴스
		int result = deliveryMapper.orderSuccess(deliveryVO);	//업데이트 결과
		resultHm.put("result", result);
		
		if (result!=0) {
			resultHm.put("orderCode", deliveryVO.getOrderCode());	//업데이트 성공 후 반환된 orderCode
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
		log.info(delivery.toString());
		
		return delivery;
	}

}
