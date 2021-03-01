package org.zerock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.OrderDetailVO;
import org.zerock.domain.OrderVO;
import org.zerock.domain.PageVO;
import org.zerock.domain.basketVO;
import org.zerock.mapper.CustomerMapper;
import org.zerock.mapper.DeliveryMapper;
import org.zerock.mapper.OrderDetailMapper;
import org.zerock.mapper.OrderMapper;
import org.zerock.mapper.ProductMapper;
import org.zerock.mapper.basketMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private basketMapper basketMapper;
	@Resource
	private DeliveryMapper deliveryMapper;
	@Resource 
	OrderDetailMapper odMapper;
	@Resource
	ProductMapper productMapper;
	@Resource
	CustomerMapper customerMapper;
	
	//�ֹ� ������ ���̺� ������ ����.
	@Override
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode) {
		OrderDetailVO odVO;
		int resultRow = 0;
			
		for (int i=0; i<productsHm.size(); i++) {
			int productCode = Integer.parseInt(productsHm.get(i).get("productCode").toString());	//��ǰ�ڵ�
			int productQuantity = Integer.parseInt(productsHm.get(i).get("productQuantity").toString());	//��ǰ����
			odVO = new OrderDetailVO(orderCode, productCode, productQuantity);
				
			resultRow += odMapper.createOrderDetail(odVO);
		}
			
		return resultRow;
	}
	
	//delivery ���ͼ��Ϳ��� �������� �� �α��ε� ������� ����ڵ����� �˾ƺ��� ���� ������
//	@Override
//	public long getCustomerCodeByDeliery(int deliveryCode) {
//		long customerCode = orderMapper.getCustomerCodeByDeliery(deliveryCode);
//		
//		return customerCode;
//	}

	@Override
	public int updateStatus(int orderCode, String orderStatus) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orderCode", orderCode);
		hm.put("orderStatus", orderStatus);
		
		return orderMapper.updateStatus(hm);
	}

	@Override
	public long getCustomerCodeByOrder(int orderCode) {
		
		return orderMapper.getCustomerCodeByOrder(orderCode);
	}

	@Override
	public Integer getOrderCode(HashMap<String, Object> orderInfo, long customerCode) {
		Integer orderCode = null;	//������ orderCode

		String reqUrl = (String) orderInfo.get("reqUrl");	//��û�� Ŭ���̾�Ʈ url
		List<HashMap<String, Object>> productsHm = (List<HashMap<String, Object>>) orderInfo.get("products");	//�ֹ��ϰ��� �ϴ� ��ǰ�� ���� ������ ��� ����.(��ǰ�ڵ�, ����, ��ǰ����)
		int totalPrice = (int) orderInfo.get("totalPrice");	//��û �� ������ �� �ֹ��ݾ�
		
		//��ǰ ������ ���������� �ٷ� �ֹ��ϱ� ��ư�� Ŭ���� ���:��ٱ��� ���̺� ������ ����
		if (reqUrl.contains("ProductDetail")) {	
			int productCode = Integer.parseInt(productsHm.get(0).get("productCode").toString());
			int productQuantity = Integer.parseInt(productsHm.get(0).get("productQuantity").toString());
			
			basketVO basketVO = new basketVO(productCode, customerCode, productQuantity);	//basketVO �ν��Ͻ� ����.
			basketMapper.getBasketProduct(basketVO);
		}
		
		try {
			OrderVO orderVO = new OrderVO(totalPrice, customerCode);
			orderMapper.createOrder(orderVO);	//�ֹ� ���̺� ������ �����ϱ�
			orderCode = orderVO.getOrderCode();	//������ �ֹ� ���̺� �������� �ֹ� �ڵ� ��������
			createOrderDetail(productsHm, orderCode);	//�ֹ��� ���̺� ������ �����ϱ�
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return orderCode;
	}

	//orderStatus=done, basket ������ ����, productStock ������Ʈ, customerPoint ������Ʈ
	@Override
	public int orderComplete(int orderCode, long customerCode, String status) {
		List<Integer> productCodeLi = odMapper.getProductCode(orderCode);	
		List<Integer> productQuantityLi = odMapper.getProductQuantity(orderCode);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		int result = 0;
		
		if(status.equals("paid")) {
			//basket ���̺� ������ ����
			hm.put("customerCode", customerCode);
			hm.put("productCodeLi", productCodeLi);
			result = basketMapper.deleteBasket(hm);
			
			//�ֹ� �Ϸ� �� �ް� �� �� ����Ʈ
			int totalPoint = 0;
			List<Integer> productPointLi = productMapper.getPoints(productCodeLi);
			for (int i=0; i<productPointLi.size(); i++) {
				totalPoint += productPointLi.get(i)*productQuantityLi.get(i);
			}
			hm.put("totalPoint", totalPoint);
			result = customerMapper.updatePoint(hm);	//�� ����Ʈ ������Ʈ
			
			//��ǰ ��� ������Ʈ(�ֹ� ������ŭ ����)
			for (int i=0; i<productCodeLi.size(); i++) {
				hm.put("productCode", productCodeLi.get(i));
				hm.put("productQuantity", productQuantityLi.get(i));
				
				result = productMapper.subStock(hm);
			}
			
			return updateStatus(orderCode, "done");	//�ش� orderCode�� orderStatus=done���� ������Ʈ
		}
		
		return result;
	}

//	@Override
//	public List<HashMap<String, Object>> getOrderDone(Integer customerCode) {
//		//���������� ������ List ��ü(���� JSON ������ ������)
//		List<HashMap<String, Object>> orDoneInfoLi = new ArrayList<HashMap<String, Object>>();
//		//���ڵ带 ���� �ش� ���� �ֹ��Ϸ�� ����Ʈ�� ���ɴϴ�.(JSON �����Ϳ��� odProInfo�� ������ ��� �����͸� �˻��մϴ�.)
//		List<HashMap<String, Object>> ordeliInfoes = orderMapper.getOrderDone(customerCode);
//		
//		for (HashMap<String, Object> ordeliInfo:ordeliInfoes) {
//			//�ϳ��� �ֹ��ڵ忡 ���� ������ ��� �ִ� �ؽø� ��ü�Դϴ�.
//			HashMap<String, Object> orDoneInfoHm = new HashMap<String, Object>();
//			
//			//�ϳ��� �ֹ��ڵ忡 ���� ������ �� ��ǰ�� ���õ�(odProInfo) �����͸� ������ ��� �����͸� �ش� �ؽø� ��ü�� �����մϴ�.
//			orDoneInfoHm.put("order_code", ordeliInfo.get("order_code"));
//			orDoneInfoHm.put("order_date", ordeliInfo.get("order_date"));
//			orDoneInfoHm.put("order_status", ordeliInfo.get("order_status"));
//			orDoneInfoHm.put("delivery_status", ordeliInfo.get("delivery_status"));
//			
//			//�ϳ��� �ֹ��ڵ忡 ���� ��� ��ǰ�鿡 ���� �����͸� ������ List ��ü�� �����մϴ�.
//			List<HashMap<String, Object>> odProInfoLi = 
//					odMapper.getDoneProOdInfo(Integer.parseInt(ordeliInfo.get("order_code").toString()));
//			
//			//�ֹ��ڵ忡 ���� ������ �� �Ʊ� ���� ���ߴ� ��ǰ�� ���� ������ �����մϴ�.
//			orDoneInfoHm.put("odProInfo", odProInfoLi);
//			
//			//���� ���ϵǴ� List ��ü�� �ϳ��� �ֹ������� �����մϴ�.
//			orDoneInfoLi.add(orDoneInfoHm);
//		}
//		
//		return orDoneInfoLi;
//	}

//	@Override
//	public List<HashMap<String, Object>> getOrderList(Integer customerCode, String orderStatus) {
//		HashMap<String, Object> hm = new HashMap<String, Object>();
//		hm.put("customerCode", customerCode);
//		hm.put("orderStatus", orderStatus);
//		
//		return getOrProList(orderMapper.getOrderList(hm));
//	}
	
	public List<HashMap<String, Object>> getOrProList(List<HashMap<String, Object>> ordeliInfoes) {
		//���������� ������ List ��ü(���� JSON ������ ������)
		List<HashMap<String, Object>> orDoneInfoLi = new ArrayList<HashMap<String, Object>>();
		
		for (HashMap<String, Object> ordeliInfo:ordeliInfoes) {
			//�ϳ��� �ֹ��ڵ忡 ���� ������ ��� �ִ� �ؽø� ��ü�Դϴ�.
			HashMap<String, Object> orDoneInfoHm = new HashMap<String, Object>();
			
			//�ϳ��� �ֹ��ڵ忡 ���� ������ �� ��ǰ�� ���õ�(odProInfo) �����͸� ������ ��� �����͸� �ش� �ؽø� ��ü�� �����մϴ�.
			orDoneInfoHm.put("order_code", ordeliInfo.get("order_code"));
			orDoneInfoHm.put("order_date", ordeliInfo.get("order_date"));
			orDoneInfoHm.put("order_status", ordeliInfo.get("order_status"));
			orDoneInfoHm.put("delivery_status", ordeliInfo.get("delivery_status"));
			
			//�ϳ��� �ֹ��ڵ忡 ���� ��� ��ǰ�鿡 ���� �����͸� ������ List ��ü�� �����մϴ�.
			List<HashMap<String, Object>> odProInfoLi = 
					odMapper.getDoneProOdInfo(Integer.parseInt(ordeliInfo.get("order_code").toString()));
			
			//�ֹ��ڵ忡 ���� ������ �� �Ʊ� ���� ���ߴ� ��ǰ�� ���� ������ �����մϴ�.
			orDoneInfoHm.put("odProInfo", odProInfoLi);
			
			//���� ���ϵǴ� List ��ü�� �ϳ��� �ֹ������� �����մϴ�.
			orDoneInfoLi.add(orDoneInfoHm);
		}
		
		return orDoneInfoLi;
	}

	@Override
	public List<HashMap<String, Object>> getOrderListLimit(Integer customerCode, String orderStatus, PageVO page) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("customerCode", customerCode);
		hm.put("orderStatus", orderStatus);
		hm.put("page", page);
		
		return getOrProList(orderMapper.getOrderListLimit(hm));
	}

	@Override
	public int getOrderCnt(Integer customerCode, String orderStatus) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("customerCode", customerCode);
		hm.put("orderStatus", orderStatus);
		
		return orderMapper.getOrderCnt(hm);
	}

	@Override
	public OrderVO getOrderInfo(int orderCode) {
		
		return orderMapper.getOrderInfo(orderCode);
	}

	@Override
	public List<HashMap<String, Object>> getProOdInfo(int orderCode) {
		// TODO Auto-generated method stub
		return odMapper.getDoneProOdInfo(orderCode);
	}

}
