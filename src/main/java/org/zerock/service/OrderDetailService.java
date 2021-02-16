package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailService {
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode);
}
