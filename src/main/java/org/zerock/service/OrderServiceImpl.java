package org.zerock.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.zerock.domain.OrderVO;
import org.zerock.mapper.OrderMapper;

@Repository
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public int createOrder(OrderVO orderVO) {
		orderMapper.createOrder(orderVO);
		int orderCode = orderVO.getOrderCode();
		
		return orderCode;
	}

}
