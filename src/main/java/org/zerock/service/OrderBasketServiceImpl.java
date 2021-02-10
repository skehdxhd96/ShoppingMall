package org.zerock.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.zerock.domain.OrderBasketVO;
import org.zerock.mapper.OrderBasketMapper;

@Repository
public class OrderBasketServiceImpl implements OrderBasketService {
	@Resource
	OrderBasketMapper obMapper;
	
	@Override
	public void createOrderBasket(OrderBasketVO orderBasketVO) {
		obMapper.createOrderBasket(orderBasketVO);
		System.out.println("orderBasket 데이터 적재 완료\norderCode : " + orderBasketVO.getOrderCode());
	}

}
