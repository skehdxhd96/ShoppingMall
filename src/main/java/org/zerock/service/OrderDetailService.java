package org.zerock.service;

import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailService {
	public void createOrderDetail(int orderCode, int productCode, int productQuantity);
	public List<Integer> getProductCodes(int orderCode);
}
