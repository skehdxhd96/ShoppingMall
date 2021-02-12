package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailMapper {
	public void createOrderDetail(OrderDetailVO odVO);
	public List<Integer> getProductCodes(int orderCode);
}
