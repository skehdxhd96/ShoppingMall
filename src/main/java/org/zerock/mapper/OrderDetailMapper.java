package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.OrderDetailVO;

public interface OrderDetailMapper {
	public int createOrderDetail(OrderDetailVO odVO);
	public List<Integer> getProductCode(int orderCode);
	public List<Integer> getProductQuantity(int orderCode);
}
