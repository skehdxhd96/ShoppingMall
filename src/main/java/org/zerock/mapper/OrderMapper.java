package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.OrderVO;

public interface OrderMapper {
	public int createOrder(OrderVO orderVO);
	//delivery ���ͼ��Ϳ��� �������� �� �α��ε� ������� ����ڵ����� �˾ƺ��� ���� ������
//	public long getCustomerCodeByDeliery(int deliveryCode);
	//����� �Է��� �Ϸ���� ��(������̺��� delivery_status�� preparing���� �ٲ��� ��) �ֹ����̺��� order_status�� done���� �ٲ�.
	public int updateStatus(HashMap<String, Object> hm);
	//order ���ͼ��Ϳ��� �������� �� �α��ε� ������� �ֹ��ڵ����� �˾ƺ��� ���� ������
	public long getCustomerCodeByOrder(int orderCode);
//	public List<HashMap<String, Object>> getOrderDone(Integer customerCode);
//	public List<HashMap<String, Object>> getOrderList(HashMap<String, Object> hm);
	public List<HashMap<String, Object>> getOrderListLimit(HashMap<String, Object> hm);
	public int getOrderCnt(HashMap<String, Object> hm);
	public OrderVO getOrderInfo(int orderCode);
	public int getTotalPrice(int orderCode);
	public int getTotalPoint(int orderCode);
}
