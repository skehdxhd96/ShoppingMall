package org.zerock.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.zerock.domain.DeliveryVO;
import org.zerock.mapper.DeliveryMapper;

import lombok.extern.log4j.Log4j;

@Repository
@Log4j
public class DeliveryServiceImpl implements DeliveryService {
	@Resource
	DeliveryMapper deliveryMapper;
	
	@Override
	public int createDelivery(int orderCode, long customerCode) {
		DeliveryVO deliveryVO = new DeliveryVO(orderCode);	//deliveryVO 인스턴스 생성
		//DeliveryMapper의 createDelivery 메서드의 해쉬맵 파라미터 인스턴스 생성
		HashMap<String, Object> parameterHm = new HashMap<String, Object>();
		parameterHm.put("deliveryVO", deliveryVO);
		parameterHm.put("customerCode", customerCode);
		
		deliveryMapper.createDelivery(parameterHm);
		deliveryVO = (DeliveryVO) parameterHm.get("deliveryVO");
		int deliveryCode = deliveryVO.getDeliveryCode();	//방금 insert한 데이터의 deliveryCode 리턴.
		
		System.out.println("delivery 테이블 데이터 적재 완료\ndeliveryCode : " + deliveryCode);
		
		return deliveryCode;
	}

	@Override
	public HashMap<String, Object> getDelivery(int deliveryCode) {
		System.out.println("getDelivery() : " + deliveryCode);
		
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
		
		System.out.println("DeliveryServiceImpl orderSuccess result:"+resultHm.toString());
		
		return resultHm;
	}

}
