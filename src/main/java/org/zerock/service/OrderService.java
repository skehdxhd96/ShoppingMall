package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zerock.domain.DeliveryVO;

public interface OrderService {
	//delivery 인터셉터에서 구매자일 때 로그인된 사용자의 배달코드인지 알아보기 위한 쿼리문
//	public long getCustomerCodeByDeliery(int deliveryCode);
	//배송지 입력이 완료됐을 때(배송테이블의 delivery_status가 preparing으로 바꼈을 때) 주문테이블의 order_status를 done으로 바꿈.
	public int updateStatus(int orderCode);
	//order 인터셉터에서 구매자일 때 로그인된 사용자의 주문코드인지 알아보기 위한 쿼리문
	public long getCustomerCodeByOrder(int orderCode);
	public Integer getOrderCode(HashMap<String, Object> orderInfo, long customerCode);
	public int createOrderDetail(List<HashMap<String, Object>> productsHm, int orderCode);
	public int orderComplete(int orderCode, long customerCode);
	//마이페이지-주문목록에서 고객코드로 주문코드, 주문일, 주문상태, 배송상태 가져오기
	public List<HashMap<String, Object>> getOrderDone(Integer customerCode);
}
