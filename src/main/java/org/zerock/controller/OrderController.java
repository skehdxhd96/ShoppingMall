package org.zerock.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.DeliveryVO;
import org.zerock.domain.basketVO;
import org.zerock.service.CustomerServiceImpl;
import org.zerock.service.DeliveryServiceImpl;
import org.zerock.service.OrderDetailServiceImpl;
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
	private OrderDetailServiceImpl odService;
	@Resource
	private DeliveryServiceImpl deliveryService;
	@Resource
	private CustomerServiceImpl customerService;
	@Resource
	private Gson gson;
	
	// 상품 상세 페이지에서 바로 주문하기 버튼 클릭했을 때 axios를 활용해서 전달받은 데이터를 DB에 적재시키는 api
	// 이거 나중에 장바구니에서 주문하기로 넘어갈 때도 사용할 것임.
	@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
	@ResponseBody
	public String delivery(@RequestBody HashMap<String, Object> deliveryHm, HttpSession session) {
		System.out.println("\n=====================================================");
		System.out.println("바로 주문하기 버튼을 클릭했을 때 axios api 만들기");
		
		JsonObject resjson = new JsonObject();	//응답 jSON 인스턴스 생성.
		//권한체크
		if (session.getAttribute("customerCode")==null) {	//로그인이 안 돼 있을 때
			resjson.addProperty("result", 0);
			
			return gson.toJson(resjson);
		}
		
		int totalPrice = 0;
		int orderCode = 0;
		int productCode = 0;
		int productQuantity = 0;
		int deliveryCode = 0;
		long customerCode = (long) session.getAttribute("customerCode");	//고객코드
		basketVO basketVO;
		
		String reqUrl = (String) deliveryHm.get("reqUrl");
		JsonArray productsArr = (JsonArray) gson.toJsonTree(deliveryHm.get("products"));	//주문하고자 하는 상품에 대한 정보가 담겨 있음.(상품코드, 수량, 상품가격)
		totalPrice = (int) deliveryHm.get("totalPrice");	//요청 시 전달한 총 주문금액
		
		if (reqUrl.contains("ProductDetail")) {	//상품 상세정보 페이지에서 바로 주문하기 버튼을 클릭한 경우
			JsonObject productObj= (JsonObject) productsArr.get(0);
			productCode = productObj.get("productCode").getAsInt();	//상품코드
			productQuantity = productObj.get("productQuantity").getAsInt();	//상품수량
			
			basketVO = new basketVO(productCode, customerCode, productQuantity);	//basketVO 인스턴스 생성.
			basketService.getBasketProduct(basketVO);	//장바구니 테이블에 데이터 적재.
		}
		
		//주문 테이블에 데이터 생성하기
		orderCode = orderServie.createOrder(totalPrice, customerCode);
		
		//주문상세 테이블에 데이터 생성하기 
		for (int i=0; i<productsArr.size(); i++) {
			JsonObject productObj = (JsonObject) productsArr.get(i);
			productCode = productObj.get("productCode").getAsInt();	//상품코드
			productQuantity = productObj.get("productQuantity").getAsInt();	//상품수량
			odService.createOrderDetail(orderCode, productCode, productQuantity);
		}
		
		//배송 테이블에 데이터 생성하기
		deliveryCode = deliveryService.createDelivery(orderCode, customerCode);
		
		//응답데이터 json 구조로 만들기 - result, deliveryCode
		resjson.addProperty("result", 1);
		resjson.addProperty("deliveryCode", deliveryCode);
		
		System.out.println("=====================================================");
		
		return gson.toJson(resjson);
	}

	@RequestMapping(value="/order/delivery/form", method=RequestMethod.GET)
	public String orderInput(@RequestParam int deliveryCode, Model model, HttpSession session) {
		System.out.println("\n=====================================================\n여기는 배송지입력 페이지");
		HashMap<String, Object> deliveryHm = deliveryService.getDelivery(deliveryCode);
		HashMap<String, Object> customerHm = customerService.getBuyerProfile((long) session.getAttribute("customerCode"));
		
		model.addAttribute("buyer", customerHm);
		model.addAttribute("recipient", deliveryHm);
		System.out.println(deliveryHm.toString());
		System.out.println("=====================================================");
		return "order/deliveryForm";
	}
}
