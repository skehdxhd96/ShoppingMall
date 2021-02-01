package org.zerock.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import org.zerock.domain.SocialDetailVO;
import org.zerock.oauth.SocialLogin;
import org.zerock.oauth.SocialValue;
import org.zerock.service.CustomerServiceImpl;
import org.zerock.service.SocialDetailServiceImpl;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
	@Inject
	private SocialValue naverValue;
	
	@Inject
	private SocialLogin naverLogin;
	
	@Inject
	private CustomerServiceImpl customerService;
	
	@Inject
	private SocialDetailServiceImpl sdService;
	
	//로그인 페이지
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		naverLogin.updateTest(naverValue);
		String naverLoginUrl = naverLogin.getAuthorizationUrl(session);
	
		model.addAttribute("naverLoginUrl", naverLoginUrl);
		
		return "login";
	}
	
	//소셜로그인 콜백
	@RequestMapping(value="/login/{social}/callback", method=RequestMethod.GET)
	public String loginCallback(Model model, @PathVariable String social, HttpSession session, 
			@RequestParam String state, @RequestParam String code, RedirectAttributes redirectAttributes) throws Exception {
		
		CustomerVO loginCustomer = null;
		OAuth2AccessToken accessToken = null;
		
		if (social.equals("naver")) {
			accessToken = naverLogin.getAccessToken(code, state, session);
			loginCustomer = naverLogin.getProfile(accessToken);
		}
		
		//유저 존재 여부 확인
		HashMap<String, Object> loginInfo = customerService.getLoginInfo(loginCustomer.getSocialId());
		
		if (loginInfo==null) {
			System.out.println("회원가입 페이지로 이동합니다.");
			redirectAttributes.addFlashAttribute("newCustomer", loginCustomer);
			
			return "redirect:/signUp";
		}
		else {
			System.out.println("존재하는 정보입니다.");
			
			long customerCode = (long) loginInfo.get("customer_code");
			
			//DB에 accessToken 저장
			//social_detail 테이블에 customer_code 데이터가 존재하는지 select
			SocialDetailVO socialDetail = sdService.findBySocialDetail(customerCode);
			
			if (socialDetail==null) {
				System.out.println("accessToken에 대한 데이터가 존재하지 않습니다.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);
				sdService.insertTokenData(socialDetail);
			}
			else {
				System.out.println("accessToken에 대한 데이터가 존재합니다.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);
				sdService.updateTokenData(socialDetail);
			}
			
			//로그인 유효성 검사를 위한 session 저장
			session.removeAttribute("oauthState"); 
			session.setAttribute("customerType", loginInfo.get("customer_type"));
			session.setAttribute("customerCode", customerCode);
			session.setAttribute("socialType", social);
			System.out.println("login 성공!");
			
			return "redirect:/";
		}
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
	public String signUpPost(CustomerVO customerInfo, HttpSession session) {
		if (customerInfo.getCustomerType()==1) {	//새로 가입한 회원이 구매자라면
			customerService.insertBuyer(customerInfo);
		}
		else {	//새로 가입한 회원이 판매자라면
			customerService.insertSeller(customerInfo);
		}
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/login/userModify")
	public String userModify(HttpSession session, Model model) {
		System.out.println("여기는 회원정보 수정 페이지");
		System.out.println(session.getAttribute("customerType"));
		HashMap<String, Object> profile = null;
		//회원 타입에 따라 필요한 프로필정보를 다르게 가져온다.
		if (session.getAttribute("customerType").equals(1)) {
			//구매자라면 이름, 이메일, 전화번호, 주소만
			profile = customerService.getBuyerProfile((long) session.getAttribute("customerCode"));	
		}
		else {
			//구매자라면 이름, 이메일, 전화번호, 회사명, 회사 전화번호, 회사 주소
			profile = customerService.getSellerProfile((long) session.getAttribute("customerCode"));
		}
		model.addAttribute("profile", profile);
		System.out.println(profile.keySet());
		
		return "/myPage/updateProfile";
	}
	
	@RequestMapping(value="/login/userDelete")
	public String userDelete(HttpSession session, Model model) {
		String reauthUrl = "";
		
		System.out.println("=========================================================");
		System.out.println("여기는 회원탈퇴 페이지");
		
		if (session.getAttribute("socialType").equals("naver")) {
			naverLogin.updateTest(naverValue);
			reauthUrl  = naverLogin.getReauthorizationUrl(session);
		}
		
		model.addAttribute("reauthUrl", reauthUrl);
		System.out.println("재인증 url : " + reauthUrl);
		System.out.println("=========================================================");
		
		return "myPage/deleteUser";
	}
	
	@RequestMapping(value="/login/reauth/{social}/callback", method=RequestMethod.GET)
	public String userDeleteCallback(HttpSession session, @PathVariable String social, 
			@RequestParam String code, @RequestParam String state) throws Exception {
		OAuth2AccessToken accessToken = null;
		
		if (social.equals("naver")) {
			naverLogin.updateTest(naverValue);
			accessToken = naverLogin.compareAccessToken(session, code, state);
		}
		
		session.removeAttribute("reauthState");
		return "reauthRedirect";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		System.out.println("로그아웃 합니다!");
		session.invalidate();
		
		return "redirect:/";
	}
}
