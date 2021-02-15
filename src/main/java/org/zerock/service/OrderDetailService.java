package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailService {
	public void createOrderDetail(int orderCode, int productCode, int productQuantity);
	public HashMap<String, Object> getProductInfo(int orderCode);
}
