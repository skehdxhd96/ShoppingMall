package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.OrderVO;
import org.zerock.domain.PageVO;

public interface OrderService {
	//delivery ���ͼ��Ϳ��� �������� �� �α��ε� ������� ����ڵ����� �˾ƺ��� ���� ������
//	public long getCustomerCodeByDeliery(int deliveryCode);
	//����� �Է��� �Ϸ���� ��(������̺��� delivery_status�� preparing���� �ٲ��� ��) �ֹ����̺��� order_status�� done���� �ٲ�.
	public int updateStatus(int orderCode, String orderStatus);
	//order ���ͼ��Ϳ��� �������� �� �α��ε� ������� �ֹ��ڵ����� �˾ƺ��� ���� ������
	public long getCustomerCodeByOrder(int orderCode);
	public Integer getOrderCode(HashMap<String, Object> orderInfo, long customerCode);
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode);
	public int orderComplete(int orderCode, long customerCode, String status);
	//����������-�ֹ���Ͽ��� ���ڵ�� �ֹ��ڵ�, �ֹ���, �ֹ�����, ��ۻ��� ��������
	//public List<HashMap<String, Object>> getOrderDone(Integer customerCode);
//	public List<HashMap<String, Object>> getOrderList(Integer customerCode, String orderStatus);
	public List<HashMap<String, Object>> getOrderListLimit(Integer customerCode, String orderStatus, PageVO page);
	public int getOrderCnt(Integer customerCode, String orderStatus);
	public OrderVO getOrderInfo(int orderCode);
	public List<HashMap<String, Object>> getProOdInfo(int orderCode);
	public int getTotalPrice(int orderCode);
}
