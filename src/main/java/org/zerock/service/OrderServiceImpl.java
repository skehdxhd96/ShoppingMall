package org.zerock.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.OrderVO;
import org.zerock.mapper.OrderMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public int createOrder(int totalPrice, long customerCode) {
		OrderVO orderVO = new OrderVO();
		orderVO.setTotalOrderPrice(totalPrice); //orderVO 인스턴스에 totalPrice 필드값 set.
		orderVO.setCustomerCode(customerCode); //orderVO 인스턴스에 customerCode 필드값 set.
		
		orderMapper.createOrder(orderVO);
		int orderCode = orderVO.getOrderCode();
		System.out.println("order 테이블 데이터 생성 완료\norderCode : " + orderCode);
	
		return orderCode;
	}

	//delivery 인터셉터에서 구매자일 때 로그인된 사용자의 배달코드인지 알아보기 위한 쿼리문
	@Override
	public long getCustomerCodeByDeliery(int deliveryCode) {
		long customerCode = orderMapper.getCustomerCodeByDeliery(deliveryCode);
		
		return customerCode;
	}

	@Override
	public int updateStatus(int orderCode) {
	
		return orderMapper.updateStatus(orderCode);
	}

	@Override
	public long getCustomerCodeByOrder(int orderCode) {
		
		return orderMapper.getCustomerCodeByOrder(orderCode);
	}

}
