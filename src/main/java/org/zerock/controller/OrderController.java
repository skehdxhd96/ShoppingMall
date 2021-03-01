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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.CustomerVO;
import org.zerock.domain.DeliveryVO;
import org.zerock.domain.OrderVO;
import org.zerock.domain.PageVO;
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
	private PageVO page;
	
	// ��ǰ �� ���������� �ٷ� �ֹ��ϱ� ��ư Ŭ������ �� axios�� Ȱ���ؼ� ���޹��� �����͸� DB�� �����Ű�� api
	// �̰� ���߿� ��ٱ��Ͽ��� �ֹ��ϱ�� �Ѿ ���� ����� ����.
	@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
	@ResponseBody
	public String delivery(@RequestBody HashMap<String, Object> orderInfo, HttpSession session) {
		log.info("\n=====================================================");
		log.info("�ٷ� �ֹ��ϱ� ��ư�� Ŭ������ �� axios api �����");
		
		JsonObject resjson = new JsonObject();	//���� jSON �ν��Ͻ� ����.
		
		//����üũ
		if (session.getAttribute("customerCode")==null) {	//�α����� �� �� ���� ��
			log.info("�α����� �ʿ��մϴ�.");
			resjson.addProperty("result", 0);
					
			return gson.toJson(resjson);
		}
		
		long customerCode = (long) session.getAttribute("customerCode");	//���ڵ�
		Integer orderCode = orderServie.getOrderCode(orderInfo, customerCode);
		
		if (orderCode==null) {
			resjson.addProperty("result", 2);
			
			return gson.toJson(resjson);
		}
		
		//���䵥���� json ������ ����� - result, deliveryCode
		resjson.addProperty("result", 1);
		resjson.addProperty("orderCode", orderCode);
				
		log.info("���Ϲ��� orderCode�� " + orderCode + " �Դϴ�.");
		log.info("=====================================================");
		
		return gson.toJson(resjson);
	}

	//����� �Է�(GET) or ����(GET) ������
	@RequestMapping(value="/order/delivery/form", method=RequestMethod.GET)
	public String deliveryFormGet(@RequestParam int orderCode, Model model, HttpSession session) {
		log.info("\n=====================================================\n����� ������Է� ������");
		DeliveryVO delivery = deliveryService.getDeliveryByOrderCode(orderCode);
		
		if (delivery==null) {	//����� �Է�
			CustomerVO customer = customerService.getBuyerProfile((long) session.getAttribute("customerCode"));
			model.addAttribute("buyer", customer);
			model.addAttribute("orderCode", orderCode);
			
			return "order/deliveryForm";
		} else {	//����� ����
			log.info("������� ������Ʈ �մϴ�.");
			delivery.setOrderCode(orderCode);
			model.addAttribute("delivery", delivery);
			
			return "order/deliveryUpdate";
		}
	}
	
	//����� �Է� API(POST)
	@RequestMapping(value="/order/delivery/form", method=RequestMethod.POST)
	@ResponseBody
	public String deliveryPOST(@RequestBody DeliveryVO deliveryVO) {
		log.info("\n=====================================================\n����� �Է��� �������ϴ�.");
		log.info(deliveryVO.toString());
		HashMap<String, Object> resHm = new HashMap<String, Object>();	//Ŭ���̾�Ʈ���� ������ ������
		int result = deliveryService.createDelivery(deliveryVO);	//��� ���̺� ������ insert
		
		resHm.put("result", result);
		resHm.put("orderCode", deliveryVO.getOrderCode());
		log.info("=====================================================");
		
		return gson.toJson(resHm);
	}
	
	//����� ���� API(PATCH)
	@RequestMapping(value="/order/delivery/form", method=RequestMethod.PATCH)
	@ResponseBody
	public String deliveryPATCH(@RequestBody DeliveryVO delivery) {
		HashMap<String, Object> resHm = new HashMap<String, Object>();
		int result = deliveryService.updateDeliveryInfo(delivery);
		resHm.put("result", result);
		
		return gson.toJson(resHm);
	}
	
	//������̺� ������Ʈ ���� orderStatus=done, basket ������ ����
	@RequestMapping(value="/order/delivery/after", method=RequestMethod.GET)
	public String deliveryAfter(@RequestParam int orderCode, @RequestParam String status, HttpSession session) {
		int result = 0;
		long customerCode = (long) session.getAttribute("customerCode");
		
		result = orderServie.orderComplete(orderCode, customerCode, status);
		
		//result�� 0(����)�̸� orderError ��������, �����̸� orderSuccess �������� �����̷�Ʈ
		return result==0? "redirect:/order/orderError" : "redirect:/order/orderSuccess?orderCode="+orderCode;
	}
	
	//�ֹ� ���� ������
	@RequestMapping(value="/order/orderSuccess", method=RequestMethod.GET) 
	public String orderSuccess(@RequestParam int orderCode, Model model) {
		DeliveryVO delivery = deliveryService.getDeliveryByOrderCode(orderCode);
		
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("delivery", delivery);
		
		return "/order/orderSuccess";
	}
	
	//�ֹ� ���� ������
	@RequestMapping("/order/orderError")
	public String orderError() {
		
		return "/order/orderError";
	}
	
	//�ֹ� ���
	@RequestMapping(value="/order/orderCancel", method=RequestMethod.POST)
	@ResponseBody
	public String orderCancel(@RequestBody HashMap<String, Object> reqHm) {
		log.info("\n===================================�ֹ���� ��û�� ���Խ��ϴ�.===================================");
		HashMap<String, Object> resHm = new HashMap<String, Object>();
		int orderCodeInt = Integer.parseInt(reqHm.get("orderCode").toString());
		
		if (orderServie.updateStatus(orderCodeInt, "cancel")==0) {
			log.info("������Ʈ �� �����Ͱ� �������� �ʽ��ϴ�.");
			resHm.put("result", 0);
			
			return gson.toJson(resHm);
		}
		else {
			log.info("orderStatus : cancel�� ����Ǿ����ϴ�. �ֹ��ڵ� : " + orderCodeInt);
			resHm.put("result", deliveryService.updateDeliveryStatus(orderCodeInt, "cancel"));
			
			return gson.toJson(resHm);
		}
	}
	
	//�������������� �ֹ� ��ҵ� ��ǰ ����
	@RequestMapping("/myPage/order/cancel")
	public String orderCancel(HttpSession session, Model model, 
			@CookieValue(value="orderCancelCnt", required=false) Cookie cookieCnt, HttpServletResponse response) {
		log.info("\n==========================�ֹ���ҵ� �ֹ� ����� Ȯ���� �� �ֽ��ϴ�.===========================");
		String cnt = Integer.toString(orderServie.getOrderCnt(Integer.parseInt(session.getAttribute("customerCode").toString()), "cancel"));
		//�ֹ� ��ҵ� �� ������ ��Ű�� ����.
		if (cookieCnt==null) {
			log.info("��Ű�� �������� ����.");
			cookieCnt = new Cookie("orderCancelCnt", cnt);
		} else if (cookieCnt.getValue()!=cnt) {
			log.info("��Ű�� �������� ����.");
			cookieCnt.setValue(cnt);
		} else {
			log.info("��Ű�� �����մϴ�.");
		}
		response.addCookie(cookieCnt);
//		Integer customerCode = Integer.parseInt(session.getAttribute("customerCode").toString());
//		List<HashMap<String, Object>> orderCancelLi = orderServie.getOrderList(customerCode, "cancel");
//		model.addAttribute("orderCancelLi", orderCancelLi);
		
		return "/myPage/orderCancelList";
	}
	
	//���������� �ֹ� ���� ����¡ ó��
	@RequestMapping(value="/myPage/order/paging", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String orderPaging(@RequestBody HashMap<String, Object> hm, HttpSession session) {
		log.info(hm.isEmpty());
		page = new PageVO(Integer.parseInt(hm.get("page").toString()), 5);
		List<HashMap<String, Object>> resHm = orderServie.getOrderListLimit(Integer.parseInt(session.getAttribute("customerCode").toString()), 
					hm.get("orderStatus").toString(), page);
		
		return gson.toJson(resHm);
	}
	
	//�ֹ��� ������
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
