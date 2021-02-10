package org.zerock.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.DeliveryVO;
import org.zerock.domain.OrderBasketVO;
import org.zerock.domain.OrderVO;
import org.zerock.domain.basketVO;
import org.zerock.service.DeliveryServiceImpl;
import org.zerock.service.OrderBasketServiceImpl;
import org.zerock.service.OrderServiceImpl;
import org.zerock.service.basketServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class OrderController {
	@Resource
	private basketServiceImpl basketService;
	@Resource
	private OrderServiceImpl orderServie;
	@Resource
	private OrderBasketServiceImpl obService;
	@Resource
	private DeliveryServiceImpl deliveryService;
	@Resource
	private Gson gson;
	
	// 상품 상세 페이지에서 바로 주문하기 버튼 클릭했을 때 axios를 활용해서 전달받은 데이터를 DB에 적재시키는 api
	// 이거 나중에 장바구니에서 주문하기로 넘어갈 때도 사용할 것임.
	@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
	@ResponseBody
	public String delivery(@RequestBody HashMap<String, Object> deliveryHm, HttpSession session) {
		System.out.println("\n=====================================================");
		System.out.println("바로 주문하기 버튼을 클릭했을 때 axios api 만들기");
		
		JsonObject resjson = new JsonObject();
		//권한체크
		if (session.getAttribute("customerCode")==null) {	//로그인이 안 돼 있을 때
			resjson.addProperty("result", 0);
			
			return gson.toJson(resjson);
		}
		else if (session.getAttribute("customerCode").equals(2)) {	//판매자일 때
			resjson.addProperty("result", 2);
			
			return gson.toJson(resjson);
		}
		
		int totalPrice = 0;
		int orderCode = 0;
		long customerCode = 0;
		int productCode = 0;
		int productQuantity = 0;
		int deliveryCode = 0;
		OrderVO orderVO = new OrderVO();
		basketVO basketVO;
		OrderBasketVO obVO;
		DeliveryVO deliveryVO;
		
		String history = (String) deliveryHm.get("history");	//상품 상세정보 테이블에서 요청했는지, 장바구니 테이블에서 요청했는지 확인용.
		JsonArray productsArr = (JsonArray) gson.toJsonTree(deliveryHm.get("products"));	//주문하고자 하는 상품에 대한 정보가 담겨 있음.(상품코드, 수량, 상품가격)
		
		if (history.equals("detail")) {	//상세 정보 페이지에서 바로 주문하기 버튼 클릭했을 때
			JsonObject productObj= (JsonObject) productsArr.get(0);
			
			totalPrice = (int) deliveryHm.get("totalPrice");	//요청 시 전달한 총 주문금액
			customerCode = (long) session.getAttribute("customerCode");	//고객코드
			productCode = productObj.get("productCode").getAsInt();	//상품코드
			productQuantity = productObj.get("productQuantity").getAsInt();	//상품수량
			
			basketVO = new basketVO(productCode, customerCode, productQuantity);	//basketVO 인스턴스 생성.
			basketService.getBasketProduct(basketVO);	//장바구니 테이블에 데이터 적재.
			orderVO.setTotalOrderPrice(totalPrice); //orderVO 인스턴스에 totalPrice 필드값 set.
			orderCode = orderServie.createOrder(orderVO);	//주문테이블 데이터 생성(데이터 생성 시 orderCode 반환)
			obVO = new OrderBasketVO(orderCode, customerCode, productCode, productQuantity);	//orderBasketVO 인스턴스 생성.
			obService.createOrderBasket(obVO); 	//주문-장바구니 테이블에 데이터 생성
			deliveryVO = new DeliveryVO(orderCode);	//deliveryVO 인스턴스 생성
			deliveryCode = deliveryService.createDelivery(deliveryVO);	//배달 테이블 데이터 생성.
		}
//		else {	//이건 장바구니 페이지에서 넘어올 때
//			for (int i=0; i<productsArr.size(); i++) {
//				JsonObject productObj= (JsonObject) productsArr.get(i);
//				System.out.println("요청데이터 : " + productObj.toString());
//				totalPrice += productObj.get("productPrice").getAsInt()*productObj.get("productQuantity").getAsInt();
//				basketVO.setCustomer_code((long) session.getAttribute("customerCode"));
//				basketVO.setProduct_code(productObj.get("productCode").getAsInt());
//				basketVO.setProduct_quantity(productObj.get("productQuantity").getAsInt());
//				//장바구니 테이블에 데이터 적재.
//				basketService.getBasketProduct(basketVO);
//			}
//		}
		
		//데이터 응답하기
		resjson.addProperty("result", 1);
		resjson.addProperty("deliveryCode", deliveryCode);
		
		System.out.println("=====================================================");
		
		return gson.toJson(resjson);
	}

	@RequestMapping(value="/order/delivery/form", method=RequestMethod.GET)
	public String orderInput(@RequestParam String deliveryCode) {
		System.out.println("\n=====================================================");
		System.out.println("여기는 배송지입력 페이지");
		
		System.out.println("=====================================================");
		return "order/deliveryForm";
	}
}
