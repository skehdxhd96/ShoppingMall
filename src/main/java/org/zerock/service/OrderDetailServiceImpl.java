package org.zerock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.OrderDetailVO;
import org.zerock.mapper.OrderDetailMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
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
