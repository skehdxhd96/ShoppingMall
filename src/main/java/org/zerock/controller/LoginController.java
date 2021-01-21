package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	//로그인 페이지
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	//회원가입 페이지
	@RequestMapping("/signUp")
	public String signUp() {
		
		return "signUp";
	}
}
