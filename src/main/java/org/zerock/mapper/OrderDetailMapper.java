package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailMapper {
	public void createOrderDetail(OrderDetailVO odVO);
	public List<HashMap<String, Object>> getProductInfo(int orderCode);
}
