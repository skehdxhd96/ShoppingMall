package org.zerock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.OrderDetailVO;
import org.zerock.domain.OrderVO;
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
	
	//주문 상세정보 테이블 데이터 적재.
	@Override
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode) {
		OrderDetailVO odVO;
		int resultRow = 0;
			
		for (int i=0; i<productsHm.size(); i++) {
			int productCode = Integer.parseInt(productsHm.get(i).get("productCode").toString());	//상품코드
			int productQuantity = Integer.parseInt(productsHm.get(i).get("productQuantity").toString());	//상품수량
			odVO = new OrderDetailVO(orderCode, productCode, productQuantity);
				
			resultRow += odMapper.createOrderDetail(odVO);
		}
			
		return resultRow;
	}
	
	//delivery 인터셉터에서 구매자일 때 로그인된 사용자의 배달코드인지 알아보기 위한 쿼리문
//	@Override
//	public long getCustomerCodeByDeliery(int deliveryCode) {
//		long customerCode = orderMapper.getCustomerCodeByDeliery(deliveryCode);
//		
//		return customerCode;
//	}

	@Override
	public int updateStatus(int orderCode) {
	
		return orderMapper.updateStatus(orderCode);
	}

	@Override
	public long getCustomerCodeByOrder(int orderCode) {
		
		return orderMapper.getCustomerCodeByOrder(orderCode);
	}

	@Override
	public Integer getOrderCode(HashMap<String, Object> orderInfo, long customerCode) {
		Integer orderCode = null;	//리턴할 orderCode

		String reqUrl = (String) orderInfo.get("reqUrl");	//요청한 클라이언트 url
		List<HashMap<String, Object>> productsHm = (List<HashMap<String, Object>>) orderInfo.get("products");	//주문하고자 하는 상품에 대한 정보가 담겨 있음.(상품코드, 수량, 상품가격)
		int totalPrice = (int) orderInfo.get("totalPrice");	//요청 시 전달한 총 주문금액
		
		//상품 상세정보 페이지에서 바로 주문하기 버튼을 클릭한 경우:장바구니 테이블에 데이터 적재
		if (reqUrl.contains("ProductDetail")) {	
			int productCode = Integer.parseInt(productsHm.get(0).get("productCode").toString());
			int productQuantity = Integer.parseInt(productsHm.get(0).get("productQuantity").toString());
			
			basketVO basketVO = new basketVO(productCode, customerCode, productQuantity);	//basketVO 인스턴스 생성.
			basketMapper.getBasketProduct(basketVO);
		}
		
		try {
			OrderVO orderVO = new OrderVO(totalPrice, customerCode);
			orderMapper.createOrder(orderVO);	//주문 테이블에 데이터 생성하기
			orderCode = orderVO.getOrderCode();	//생성된 주문 테이블 데이터의 주문 코드 가져오기
			createOrderDetail(productsHm, orderCode);	//주문상세 테이블에 데이터 생성하기
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return orderCode;
	}

	//orderStatus=done, basket 데이터 삭제, productStock 업데이트, customerPoint 업데이트
	@Override
	public int orderComplete(int orderCode, long customerCode) {
		List<Integer> productCodeLi = odMapper.getProductCode(orderCode);	
		List<Integer> productQuantityLi = odMapper.getProductQuantity(orderCode);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		int result = 0;
		
		//basket 테이블 데이터 삭제
		hm.put("customerCode", customerCode);
		hm.put("productCodeLi", productCodeLi);
		result = basketMapper.deleteBasket(hm);
		
		//주문 완료 시 받게 될 총 포인트
		int totalPoint = 0;
		List<Integer> productPointLi = productMapper.getPoints(productCodeLi);
		for (int i=0; i<productPointLi.size(); i++) {
			totalPoint += productPointLi.get(i)*productQuantityLi.get(i);
		}
		hm.put("totalPoint", totalPoint);
		result = customerMapper.updatePoint(hm);	//고객 포인트 업데이트
		
		//상품 재고 업데이트(주문 수량만큼 차감)
		for (int i=0; i<productCodeLi.size(); i++) {
			hm.put("productCode", productCodeLi.get(i));
			hm.put("productQuantity", productQuantityLi.get(i));
			
			result = productMapper.subStock(hm);
		}
		
		result = orderMapper.updateStatus(orderCode);	//해당 orderCode의 orderStatus=done으로 업데이트
		
		return result;
	}

	@Override
	public List<HashMap<String, Object>> getOrderDone(Integer customerCode) {
		List<HashMap<String, Object>> ordeliInfo = orderMapper.getOrderDone(customerCode);
		
		List<Integer> orderCodes = new ArrayList<Integer>();
		for (int i=0; i<ordeliInfo.size(); i++) {
			orderCodes.add(Integer.parseInt(ordeliInfo.get(i).get("order_code").toString()));
		}
		
		List<HashMap<String, Object>> orDoneInfoLi = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> odProInfoLi = odMapper.getDoneProOdInfo(orderCodes);
		
		int odProIdx=0;
		
		for (int i=0; i<ordeliInfo.size(); i++) {
			HashMap<String, Object> orDoneInfoHm = new HashMap<String, Object>();
			
			orDoneInfoHm.put("order_code", ordeliInfo.get(i).get("order_code"));
			orDoneInfoHm.put("order_date", ordeliInfo.get(i).get("order_date"));
			orDoneInfoHm.put("order_status", ordeliInfo.get(i).get("order_status"));
			orDoneInfoHm.put("delivery_status", ordeliInfo.get(i).get("delivery_status"));
			
			List<HashMap<String, Object>> odProInfoIdxLi = new ArrayList<HashMap<String, Object>>();
			try {
				while(orderCodes.get(i).toString().equals(odProInfoLi.get(odProIdx).get("order_code").toString())) {
					HashMap<String, Object> odProInfoIdxHm = new HashMap<String, Object>();

					odProInfoIdxHm.put("product_code", odProInfoLi.get(odProIdx).get("product_code"));
					odProInfoIdxHm.put("product_name", odProInfoLi.get(odProIdx).get("product_name"));
					odProInfoIdxHm.put("thumbnail_url", odProInfoLi.get(odProIdx).get("thumbnail_url"));
					odProInfoIdxHm.put("product_quantity", odProInfoLi.get(odProIdx).get("product_quantity"));		
					odProInfoIdxLi.add(odProInfoIdxHm);
					
					odProIdx++;
				}
			} catch(IndexOutOfBoundsException e) {
				log.info(e.getMessage());
			}
			orDoneInfoHm.put("odProInfo", odProInfoIdxLi);
			orDoneInfoLi.add(orDoneInfoHm);
		}
		
		return orDoneInfoLi;
	}

}
