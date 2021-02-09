package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	@RequestMapping("/order/delivery/form")
	public String orderInput() {
		
		return "order/deliveryForm";
	}
}
