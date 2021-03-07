package org.zerock.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.CustomerVO;
import org.zerock.domain.DeliveryVO;
import org.zerock.domain.OrderVO;
import org.zerock.domain.PageDTO;
import org.zerock.service.CustomerServiceImpl;
import org.zerock.service.DeliveryServiceImpl;
import org.zerock.service.OrderServiceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class OrderController {
	@Resource
	private OrderServiceImpl orderServie;
	@Resource
	private DeliveryServiceImpl deliveryService;
	@Resource
	private CustomerServiceImpl customerService;
	@Resource
	private Gson gson;
	private PageDTO page;
	
	// 주문하기 버튼 클릭했을 때 axios를 활용해서 전달받은 데이터를 DB에 적재시키는 api
	@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
	@ResponseBody
	public String delivery(@RequestBody HashMap<String, Object> orderInfo, HttpSession session) {
		log.info("\n=====================================================");
		log.info("주문하기 버튼을 클릭했을 때 axios api 만들기");
		
		JsonObject resjson = new JsonObject();	//응답 jSON 인스턴스 생성.
		
		//권한체크
		if (session.getAttribute("customerCode")==null) {	//로그인이 안 돼 있을 때
			log.info("로그인이 필요합니다.");
			resjson.addProperty("result", 0);
					
			return gson.toJson(resjson);
		}
		
		long customerCode = (long) session.getAttribute("customerCode");	//고객코드
		Integer orderCode = orderServie.getOrderCode(orderInfo, customerCode);
		
		if (orderCode==null) {
			resjson.addProperty("result", 2);
			
			return gson.toJson(resjson);
		}
		
		//응답데이터 json 구조로 만들기 - result, deliveryCode
		resjson.addProperty("result", 1);
		resjson.addProperty("orderCode", orderCode);
				
		log.info("리턴받은 orderCode는 " + orderCode + " 입니다.");
		log.info("=====================================================");
		
		return gson.toJson(resjson);
	}

	//배송지 입력(GET) or 수정(GET) 페이지
	@RequestMapping(value="/order/delivery/form", method=RequestMethod.GET)
	public String deliveryFormGet(@RequestParam int orderCode, Model model, HttpSession session) {
		log.info("\n=====================================================\n여기는 배송지입력 페이지");
		log.info("This is form get!!!");
		DeliveryVO delivery = deliveryService.getDeliveryByOrderCode(orderCode);
		
		if (delivery==null) {	//배송지 입력
			CustomerVO customer = customerService.getBuyerProfile((long) session.getAttribute("customerCode"));
			model.addAttribute("buyer", customer);
			model.addAttribute("orderCode", orderCode);
			model.addAttribute("getPoint", customerService.getPoint((long) session.getAttribute("customerCode")));
			model.addAttribute("getTotalPrice", orderServie.getTotalPrice(orderCode));
			model.addAttribute("getTotalPoint", orderServie.getTotalPoint(orderCode));
			return "order/deliveryForm";
		} else {	//배송지 변경
			log.info("배송지를 업데이트 합니다.");
			delivery.setOrderCode(orderCode);
			model.addAttribute("delivery", delivery);
			
			return "order/deliveryUpdate";
		}
	}
	
	//배송지 입력 API(POST)
	@RequestMapping(value="/order/delivery/form", method=RequestMethod.POST)
	@ResponseBody
	public String deliveryPOST(@RequestBody DeliveryVO deliveryVO) {
		log.info("\n=====================================================\n배송지 입력이 끝났습니다.");
		HashMap<String, Object> resHm = new HashMap<String, Object>();	//클라이언트에게 전달할 데이터
		int result = deliveryService.createDelivery(deliveryVO);	//배송 테이블 데이터 insert
		
		resHm.put("result", result);
		resHm.put("orderCode", deliveryVO.getOrderCode());
		log.info("=====================================================");
		
		return gson.toJson(resHm);
	}
	
	//배송지 변경 API(PATCH)
	@RequestMapping(value="/order/delivery/update", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deliveryPATCH(@RequestBody HashMap<String, Object> updeatedDeli) {
		log.info("This is form put!!!");
		log.info(updeatedDeli);
		HashMap<String, Object> resHm = new HashMap<String, Object>();
		int result = deliveryService.updateDeliveryInfo(updeatedDeli);
		resHm.put("result", result);
		
		return gson.toJson(resHm);
	}
	
	//배송테이블 업데이트 이후 orderStatus=done, basket 데이터 삭제
	@RequestMapping(value="/order/delivery/after", method=RequestMethod.GET)
	public String deliveryAfter(@RequestParam int orderCode, @RequestParam String status, HttpSession session) {
		int result = 0;
		long customerCode = (long) session.getAttribute("customerCode");
		
		result = orderServie.orderComplete(orderCode, customerCode, status);
		
		//result가 0(실패)이면 orderError 페이지로, 성공이면 orderSuccess 페이지로 리다이렉트
		return result==0? "redirect:/order/orderError" : "redirect:/order/orderSuccess?orderCode="+orderCode;
	}
	
	//주문 성공 페이지
	@RequestMapping(value="/order/orderSuccess", method=RequestMethod.GET) 
	public String orderSuccess(@RequestParam int orderCode, Model model) {
		DeliveryVO delivery = deliveryService.getDeliveryByOrderCode(orderCode);
		
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("delivery", delivery);
		
		return "/order/orderSuccess";
	}
	
	//주문 실패 페이지
	@RequestMapping("/order/orderError")
	public String orderError() {
		
		return "/order/orderError";
	}
	
	//주문 취소
	@RequestMapping(value="/order/orderCancel", method=RequestMethod.POST)
	@ResponseBody
	public String orderCancel(@RequestBody HashMap<String, Object> reqHm) {
		log.info("\n===================================주문취소 요청이 들어왔습니다.===================================");
		HashMap<String, Object> resHm = new HashMap<String, Object>();
		int orderCodeInt = Integer.parseInt(reqHm.get("orderCode").toString());
		
		if (orderServie.updateStatus(orderCodeInt, "cancel")==0) {
			log.info("업데이트 될 데이터가 존재하지 않습니다.");
			resHm.put("result", 0);
			
			return gson.toJson(resHm);
		}
		else {
			log.info("orderStatus : cancel로 변경되었습니다. 주문코드 : " + orderCodeInt);
			resHm.put("result", deliveryService.updateDeliveryStatus(orderCodeInt, "cancel"));
			
			return gson.toJson(resHm);
		}
	}
	
	//마이페이지에서 주문 취소된 상품 보기
	@RequestMapping("/myPage/order/cancel")
	public String orderCancel(HttpSession session, Model model, 
			@CookieValue(value="orderCancelCnt", required=false) Cookie cookieCnt, HttpServletResponse response) {
		log.info("\n==========================주문취소된 주문 목록을 확인할 수 있습니다.===========================");
		String cnt = Integer.toString(orderServie.getOrderCnt(Integer.parseInt(session.getAttribute("customerCode").toString()), "cancel"));
		//주문 취소된 총 개수를 쿠키로 저장.
		if (cookieCnt==null) {
			log.info("쿠키가 존재하지 않음.");
			cookieCnt = new Cookie("orderCancelCnt", cnt);
		} else if (cookieCnt.getValue()!=cnt) {
			log.info("쿠키가 동일하지 않음.");
			cookieCnt.setValue(cnt);
		} else {
			log.info("쿠키가 존재합니다.");
		}
		response.addCookie(cookieCnt);
//		Integer customerCode = Integer.parseInt(session.getAttribute("customerCode").toString());
//		List<HashMap<String, Object>> orderCancelLi = orderServie.getOrderList(customerCode, "cancel");
//		model.addAttribute("orderCancelLi", orderCancelLi);
		
		return "/myPage/orderCancelList";
	}
	
	//마이페이지 주문 관련 페이징 처리
	@RequestMapping(value="/myPage/order/paging", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String orderPaging(@RequestBody HashMap<String, Object> hm, HttpSession session) {
		log.info(hm.isEmpty());
		page = new PageDTO(Integer.parseInt(hm.get("page").toString()), 5);
		List<HashMap<String, Object>> resHm = orderServie.getOrderListLimit(Integer.parseInt(session.getAttribute("customerCode").toString()), 
					hm.get("orderStatus").toString(), page);
		
		return gson.toJson(resHm);
	}
	
	//주문상세 페이지
	@RequestMapping(value="/order/detail")
	public String orderDetail(@RequestParam int orderCode, Model model) {
		OrderVO order = orderServie.getOrderInfo(orderCode);
		order.setOrderCode(orderCode);
		List<HashMap<String, Object>> proOdInfo = orderServie.getProOdInfo(orderCode);
		DeliveryVO delivery = deliveryService.getDeliveryByOrderCode(orderCode);
		
		model.addAttribute("order", order);
		model.addAttribute("odPro", proOdInfo);
		model.addAttribute("delivery", delivery);
		
		return "/myPage/orderDetail";
	}
}
