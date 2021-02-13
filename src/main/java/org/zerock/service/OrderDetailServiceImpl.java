package org.zerock.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.zerock.domain.OrderDetailVO;
import org.zerock.mapper.OrderDetailMapper;

@Repository
public class OrderDetailServiceImpl implements OrderDetailService {
	@Resource
	OrderDetailMapper odMapper;
	
	@Override
	public void createOrderDetail(int orderCode, int productCode, int productQuantity) {
		OrderDetailVO odVO = new OrderDetailVO(orderCode, productCode, productQuantity);
		odMapper.createOrderDetail(odVO);
		System.out.println("orderDetail 데이터 적재 완료\n" + odVO.toString());
	}

	@Override
	public List<Integer> getProductCodes(int orderCode) {
		
		return odMapper.getProductCodes(orderCode);
	}

}
