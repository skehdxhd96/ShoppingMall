package org.zerock.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.domain.CustomerVO;
import org.zerock.oauth.SocialLogin;
import org.zerock.oauth.SocialValue;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
	@Inject
	private SocialValue naverLogin;
	
	@Inject
	private SocialLogin naver;
	
	//로그인 페이지
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		naver = new SocialLogin(naverLogin, session);
		String naverLoginUrl = naver.getAuthorizationUrl();
	
		model.addAttribute("naverLoginUrl", naverLoginUrl);
		
		return "login";
	}
	
	//소셜로그인 콜백
	@RequestMapping(value="/login/{social}/callback", method=RequestMethod.GET)
	public void loginCallback(Model model, @PathVariable String social, HttpSession session, @RequestParam String state, @RequestParam String code) throws Exception {
		if (social.equals("naver")) {
			OAuth2AccessToken accessToken = naver.getAccessToken(code, state, session);
			CustomerVO customerVO = naver.getProfile(accessToken);
			
			System.out.println(customerVO.getCustomerName());
			
		}
	}
	
	//회원가입 페이지
	@RequestMapping("/signUp")
	public String signUp() {
		
		return "signUp";
	}
}
