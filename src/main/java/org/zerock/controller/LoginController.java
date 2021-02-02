package org.zerock.controller;

import java.util.HashMap;
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
		//인증을 위한 인증 api url 페이지
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
		
		//나중에 소셜로그인 종류가 카카오도 추가될 것을 대비해 socialType에 따라 accessToken 받는 방법이 달라져서 if문 사용.
		if (social.equals("naver")) {
			accessToken = naverLogin.getAccessToken(code, state, session);
			loginCustomer = naverLogin.getProfile(accessToken);
		}
		
		//유저 존재 여부 확인
		HashMap<String, Object> loginInfo = customerService.getLoginInfo(loginCustomer.getSocialId());
		
		if (loginInfo==null) {	//customer 테이블에 데이터가 존재하지 않으면
			System.out.println("회원가입 페이지로 이동합니다.");
			redirectAttributes.addFlashAttribute("newCustomer", loginCustomer);
			
			return "redirect:/signUp";
		}
		else {	//customer 테이블에 데이터가 존재하면
			System.out.println("존재하는 정보입니다.");
			
			long customerCode = (long) loginInfo.get("customer_code");
			
			//DB에 accessToken 저장
			//social_detail 테이블에 데이터가 존재하는지 확인
			SocialDetailVO socialDetail = sdService.findBySocialDetail(customerCode);
			
			if (socialDetail==null) {	//social_detail 테이블에 데이터가 존재하지 않으면
				System.out.println("accessToken에 대한 데이터가 존재하지 않습니다.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);	//해당 accessToken 데이터 insert
				sdService.insertTokenData(socialDetail);
			}
			else {	//social_detail 테이블에 데이터가 존재한다면
				System.out.println("accessToken에 대한 데이터가 존재합니다.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);	//accessToken 데이터 update
				sdService.updateTokenData(socialDetail);
			}
			
			//로그인 유효성 검사를 위한 session 저장 - 고객유형, 고객코드, 소셜로그인유형 저장.
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
		
		//소셜로그인으로 로그인을 했다면 당연히 flashMap에 데이터가 존재할거고
		//나중에 일반로그인을 구현한다면 flashMap에 데이터가 null일 것임.
		if (flashMap!=null) {
			//redirect로 로그인페이지에서 가져온 새 고객 프로필 데이터
			CustomerVO newCustomer = (CustomerVO) flashMap.get("newCustomer");
			model.addAttribute("newCustomer", newCustomer);
		}
		
		return "signUp";
	}
	
	//회원가입 페이지에서 submit 했을 때
	@RequestMapping(value="/signUp", method=RequestMethod.POST) 
	public String signUpPost(CustomerVO customerInfo, HttpSession session) {
		if (customerInfo.getCustomerType()==1) {	//새로 가입한 회원이 구매자라면
			customerService.insertBuyer(customerInfo);
		}
		else {	//새로 가입한 회원이 판매자라면
			customerService.insertSeller(customerInfo);
		}
		
		//재로그인을 위해 session 무효화.
		session.invalidate();
		
		return "redirect:/";
	}
	
	//회원정보 수정하기 - 여기서는 재인증 필요 없음.
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
		
		//customer 테이블에서 가져온 프로필 데이터를 view에 전달.
		model.addAttribute("profile", profile);
		
		return "/myPage/updateProfile";
	}
	
	//회원탈퇴 페이지
	@RequestMapping(value="/login/userDelete")
	public String userDelete(HttpSession session, Model model) {
		String reauthUrl = "";
		
		System.out.println("=========================================================");
		System.out.println("여기는 회원탈퇴 페이지");
		
		//이것도 소셜로그인 종류에 카카오가 생길 경우를 대비해 if문 작성. -> 소셜로그인마다 재인증을 위한 api url이 다르기 때문
		if (session.getAttribute("socialType").equals("naver")) {
			reauthUrl  = naverLogin.getReauthorizationUrl(session);	//재인증 api url
		}
		
		//재인증 api url을 view에 전달.
		model.addAttribute("reauthUrl", reauthUrl);
		System.out.println("=========================================================");
		
		return "myPage/deleteUser";
	}
	
	//회원탈퇴를 위한 재인증시 콜백 페이지
	@RequestMapping(value="/login/reauth/{social}/callback", method=RequestMethod.GET)
	public String userDeleteCallback(HttpSession session, @PathVariable String social, 
			@RequestParam String code, @RequestParam String state) throws Exception {
		System.out.println("\n여기는 회원탈퇴시 재인증 콜백 페이지");
		boolean isComplete = false;	//회원탈퇴가 socialLogin 클래스에서 성공했는지를 담기 위한 boolean형 변수
		
		//이것도 카카오와 네이버의 회원 탈퇴 방식이 다를 것이기 때문에 우선 if문으로 작성.
		if (social.equals("naver")) {
			isComplete = naverLogin.compareAccessToken(session, code, state);
			System.out.println("isComplete : " + isComplete);
		}
		//재인증이 끝났으므로 session에서 reauthState 삭제.
		session.removeAttribute("reauthState");
		
		if (isComplete==true) {	//탈퇴 처리(토큰만 만료)가 성공했으면
			System.out.println("deleteToken 삭제를 완료했습니다.");
			System.out.println("해당 고객의 데이터를 DB에서 삭제합니다.");
			//토큰만 만료 되었으므로 customer 테이블에서 해당 customerCode에 대한 데이터 삭제
			customerService.deleteCustomer((long) session.getAttribute("customerCode"));
			session.invalidate();	//회원 탈퇴가 정말로 완료되었으므로 session값 무효화.
			
			return "redirect:/";
		}
		else {
			//회원탈퇴 실패시 에러페이지로 이동.
			return "redirect:/login/userDelete/error";
		}
	}
	
	//회원탈퇴 과정에 실패했을 때 이동하는 오류페이지
	@RequestMapping(value="/login/userDelete/error", method=RequestMethod.POST)
	public String userDeleteError() {
	
		return "myPage/userDeleteError";
	}
	
	//로그아웃 페이지
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		System.out.println("로그아웃 합니다!");
		session.invalidate();	//로그아웃 되었으므로 세션 만료.
		
		return "redirect:/";
	}
}
