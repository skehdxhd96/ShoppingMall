package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class MainController {

	@RequestMapping("/")
	public String toMainPage() {
		
		return "/mainPage";
	}
	
	@RequestMapping("/list")
	public String toProductList() {
		
		return "/ProductList";
	}
	
	@RequestMapping("/detail")
	public String toProductDetail() {
		
		return "/ProductDetail";
	}
}
