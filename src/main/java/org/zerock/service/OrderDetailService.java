package org.zerock.service;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailService {
	public void createOrderDetail(int orderCode, int productCode, int productQuantity);
}
