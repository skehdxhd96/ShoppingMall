package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.mapper.OrderDetailMapper;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Resource
	OrderDetailMapper odMapper;
	
	@Resource
	private OrderDetailServiceImpl odService;
	
	@Override
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode) {
		// TODO Auto-generated method stub
		return 0;
	}

}
