package org.zerock.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.OrderVO;
import org.zerock.domain.basketVO;
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
	private Gson gson;
	
	// 상품 상세 페이지에서 바로 주문하기 버튼 클릭했을 때 axios를 활용해서 전달받은 데이터를 DB에 적재시키는 api
	// 이거 나중에 장바구니에서 주문하기로 넘어갈 때도 사용할 것임.
	@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
	@ResponseBody
	public int delivery(@RequestBody HashMap<String, Object> deliveryHm, HttpSession session) {
		System.out.println("\n=====================================================");
		System.out.println("바로 주문하기 버튼을 클릭했을 때 axios api 만들기");
		
		if (session.getAttribute("customerCode")==null) {
			return 0;
		}
		else if (session.getAttribute("customerCode").equals(2)) {
			return 2;
		}
		
		int totalPrice = 0;
		basketVO basketVO = new basketVO();
		OrderVO orderVO = new OrderVO();
		
		String history = (String) deliveryHm.get("history");
		JsonArray productsArr = (JsonArray) gson.toJsonTree(deliveryHm.get("products"));
		
		if (history.equals("detail")) {	//상세 정보 페이지에서 바로 주문하기 버튼 클릭했을 때
			JsonObject productObj= (JsonObject) productsArr.get(0);
			System.out.println("요청데이터 : " + productObj.toString());
			totalPrice += productObj.get("productPrice").getAsInt()*productObj.get("productQuantity").getAsInt();

			basketVO.setCustomer_code((long) session.getAttribute("customerCode"));
			basketVO.setProduct_code(productObj.get("productCode").getAsInt());
			basketVO.setProduct_quantity(productObj.get("productQuantity").getAsInt());
			//장바구니 테이블에 데이터 적재.
			basketService.getBasketProduct(basketVO);
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
		
		//주문테이블 데이터 생성
		orderVO.setTotalOrderPrice(totalPrice);
		int orderCode = orderServie.createOrder(orderVO);
		
		//주문-장바구니 테이블에 데이터 생성
		
		
		System.out.println("=====================================================");
		return 1;
	}

	@RequestMapping("/order/delivery/form")
	public String orderInput() {

		return "order/deliveryForm";
	}
}
