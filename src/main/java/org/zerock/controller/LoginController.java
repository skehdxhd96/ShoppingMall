package org.zerock.controller;

import java.net.http.HttpRequest;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.zerock.domain.CustomerVO;
import org.zerock.oauth.SocialLogin;
import org.zerock.oauth.SocialValue;
import org.zerock.service.CustomerServiceImpl;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
	@Inject
	private SocialValue naverLogin;
	
	@Inject
	private SocialLogin naver;
	
	@Inject
	private CustomerServiceImpl customerService;
	
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
	public String loginCallback(Model model, @PathVariable String social, HttpSession session, 
			@RequestParam String state, @RequestParam String code, RedirectAttributes redirectAttributes) throws Exception {
		CustomerVO loginCustomer = null;
		
		if (social.equals("naver")) {
			OAuth2AccessToken accessToken = naver.getAccessToken(code, state, session);
			loginCustomer = naver.getProfile(accessToken);
		}
		
		//유저 존재 여부 확인
		CustomerVO checkCustomer = customerService.isCustomer(loginCustomer.getSocialId());
		if (checkCustomer==null) {
			System.out.println("회원가입 페이지로 이동합니다.");
			redirectAttributes.addFlashAttribute("newCustomer", loginCustomer);
			
			return "redirect:/signUp";
		}
		else {
			System.out.println("존재하는 정보입니다.");
		}
		return "/";
	}
	
	//회원가입 페이지
	@RequestMapping(value="/signUp", method=RequestMethod.GET)
	public String signUpGet(Model model, HttpServletRequest request) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		
		if (flashMap!=null) {
			CustomerVO newCustomer = (CustomerVO) flashMap.get("newCustomer");
			model.addAttribute("newCustomer", newCustomer);
		}
		
		return "signUp";
	}
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST) 
	public String signUpPost() {
		
		return "signUp";
	}
}
