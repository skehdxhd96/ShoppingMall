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
	
	@Override
	public void createOrderDetail(int orderCode, int productCode, int productQuantity) {
		OrderDetailVO odVO = new OrderDetailVO(orderCode, productCode, productQuantity);
		odMapper.createOrderDetail(odVO);
		System.out.println("orderDetail 데이터 적재 완료\n" + odVO.toString());
	}

	@Override
	public HashMap<String, Object> getProductInfo(int orderCode) {
		List<HashMap<String, Object>> productInfoList = odMapper.getProductInfo(orderCode);
		HashMap<String, Object> productInfoHm = new HashMap<String, Object>();
		List<Long> productCodeList = new ArrayList<Long>();
		List<Long> productQuantityList = new ArrayList<Long>();
		
		for (int i=0; i<productInfoList.size(); i++) {
			productCodeList.add((long) productInfoList.get(i).get("product_code"));
			productQuantityList.add((long) productInfoList.get(i).get("product_quantity"));
		}
		
		productInfoHm.put("productCodeList", productCodeList);
		productInfoHm.put("productQuantityList", productQuantityList);
		productInfoHm.put("result", productInfoList.size());
		
		return productInfoHm;
	}

}
