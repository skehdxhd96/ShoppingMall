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

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class LoginController {
	@Inject
	private SocialValue naverValue;
	
	@Inject
	private SocialLogin naverLogin;
	
	@Inject
	private CustomerServiceImpl customerService;
	
	@Inject
	private SocialDetailServiceImpl sdService;
	
	//�α��� ������
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		//������ ���� ���� api url ������
		String naverLoginUrl = naverLogin.getAuthorizationUrl(session);
	
		model.addAttribute("naverLoginUrl", naverLoginUrl);
		
		return "login";
	}
	
	//�Ҽȷα��� �ݹ�
	@RequestMapping(value="/login/{social}/callback", method=RequestMethod.GET)
	public String loginCallback(Model model, @PathVariable String social, HttpSession session, 
			@RequestParam String state, @RequestParam String code, RedirectAttributes redirectAttributes) throws Exception {
		
		CustomerVO loginCustomer = null;
		OAuth2AccessToken accessToken = null;
		
		//���߿� �Ҽȷα��� ������ īī���� �߰��� ���� ����� socialType�� ���� accessToken �޴� ����� �޶����� if�� ���.
		if (social.equals("naver")) {
			accessToken = naverLogin.getAccessToken(code, state, session);
			loginCustomer = naverLogin.getProfile(accessToken);
		}
		
		//���� ���� ���� Ȯ��
		HashMap<String, Object> loginInfo = customerService.getLoginInfo(loginCustomer.getSocialId());
		
		if (loginInfo==null) {	//customer ���̺� �����Ͱ� �������� ������
			log.info("ȸ������ �������� �̵��մϴ�.");
			redirectAttributes.addFlashAttribute("newCustomer", loginCustomer);
			
			return "redirect:/signUp";
		}
		else {	//customer ���̺� �����Ͱ� �����ϸ�
			log.info("�����ϴ� �����Դϴ�.");
			
			long customerCode = (long) loginInfo.get("customer_code");
			
			//DB�� accessToken ����
			//social_detail ���̺� �����Ͱ� �����ϴ��� Ȯ��
			SocialDetailVO socialDetail = sdService.findBySocialDetail(customerCode);
			
			if (socialDetail==null) {	//social_detail ���̺� �����Ͱ� �������� ������
				log.info("accessToken�� ���� �����Ͱ� �������� �ʽ��ϴ�.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);	//�ش� accessToken ������ insert
				sdService.insertTokenData(socialDetail);
			}
			else {	//social_detail ���̺� �����Ͱ� �����Ѵٸ�
				log.info("accessToken�� ���� �����Ͱ� �����մϴ�.");
				socialDetail = naverLogin.getSocialDetail(customerCode, accessToken);	//accessToken ������ update
				sdService.updateTokenData(socialDetail);
			}
			
			//�α��� ��ȿ�� �˻縦 ���� session ���� - ������, ���ڵ�, �Ҽȷα������� ����.
			session.removeAttribute("oauthState"); 
			session.setAttribute("customerType", loginInfo.get("customer_type"));
			session.setAttribute("customerCode", customerCode);
			session.setAttribute("socialType", social);
			log.info("login ����!");
			
			return "redirect:/";
		}
	}
	
	//ȸ������ ������
	@RequestMapping(value="/signUp", method=RequestMethod.GET)
	public String signUpGet(Model model, HttpServletRequest request) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		
		//�Ҽȷα������� �α����� �ߴٸ� �翬�� flashMap�� �����Ͱ� �����ҰŰ�
				//���߿� �Ϲݷα����� �����Ѵٸ� flashMap�� �����Ͱ� null�� ����.
		if (flashMap!=null) {
			//redirect�� �α������������� ������ �� �� ������ ������
			CustomerVO newCustomer = (CustomerVO) flashMap.get("newCustomer");
			model.addAttribute("newCustomer", newCustomer);
		}
		
		return "signUp";
	}
	
	//ȸ������ ���������� submit ���� ��
	@RequestMapping(value="/signUp", method=RequestMethod.POST) 
	public String signUpPost(CustomerVO customerInfo, HttpSession session) {
		log.info(customerInfo);
		if (customerInfo.getCustomerType()==1) {	//���� ������ ȸ���� �����ڶ��
			customerService.insertBuyer(customerInfo);
		}
		else {	//���� ������ ȸ���� �Ǹ��ڶ��
			customerService.insertSeller(customerInfo);
		}
		
		//��α����� ���� session ��ȿȭ.
		session.invalidate();
		
		return "redirect:/";
	}
	
	//ȸ������ �����ϱ� - ���⼭�� ������ �ʿ� ����.
	@RequestMapping(value="/login/userModify")
	public String userModifyGET(HttpSession session, Model model) {
		log.info("����� ȸ������ ���� ������");
		CustomerVO profile = null;
		
		//ȸ�� Ÿ�Կ� ���� �ʿ��� ������������ �ٸ��� �����´�.
		if (session.getAttribute("customerType").equals(1)) {
			//�����ڶ�� �̸�, �̸���, ��ȭ��ȣ, �ּҸ�
			profile = customerService.getBuyerProfile((long) session.getAttribute("customerCode"));	
		}
		else {
			//�Ǹ��ڶ�� �̸�, �̸���, ��ȭ��ȣ, ȸ���, ȸ�� ��ȭ��ȣ, ȸ�� �ּ�
			profile = customerService.getSellerProfile((long) session.getAttribute("customerCode"));
		}
		//customer ���̺��� ������ ������ �����͸� view�� ����.
		log.info(profile);
		model.addAttribute("profile", profile);
		model.addAttribute("customerCode", session.getAttribute("customerCode"));
		
		return "/myPage/updateProfile";
	}
	
	@RequestMapping(value="/login/userModify/{customerCode}", method=RequestMethod.PUT)
	public String userModifyPOST(HttpSession session, @PathVariable Long customerCode,  CustomerVO updateCustomer) {
		log.info("ȸ������ ���� ���������� ���� ��ư�� �������ϴ�");
		log.info("������ ȸ�� ����");
		log.info(updateCustomer);
		
		//�޾ƿ� ������Ʈ ������ �ڽ��� ���ڵ� ����.
		updateCustomer.setCustomerCode(customerCode);
		
		//ȸ�� Ÿ�Կ� ���� ������ �� �ִ� ������ ������ �ٸ���.
		if (session.getAttribute("customerType").equals(1)) {	//�����ڶ�� �̸�, �̸���, ��ȭ��ȣ, �ּҸ�		
			customerService.updateBuyer(updateCustomer);
		}
		else {	//�Ǹ��ڶ�� �̸�, �̸���, ��ȭ��ȣ, ȸ���, ȸ�� ��ȭ��ȣ, ȸ�� �ּ�
			customerService.updateSeller(updateCustomer);
		}
		
		return "redirect:/login/userModify";
	}
	
	//ȸ��Ż�� ������
	@RequestMapping(value="/login/userDelete")
	public String userDelete(HttpSession session, Model model) {
		String reauthUrl = "";
		
		log.info("=========================================================");
		log.info("����� ȸ��Ż�� ������");
		
		//�̰͵� �Ҽȷα��� ������ īī���� ���� ��츦 ����� if�� �ۼ�. -> �Ҽȷα��θ��� �������� ���� api url�� �ٸ��� ����
		if (session.getAttribute("socialType").equals("naver")) {
			reauthUrl  = naverLogin.getReauthorizationUrl(session);	//������ api url
		}
		
		//������ api url�� view�� ����.
		model.addAttribute("reauthUrl", reauthUrl);
		log.info("=========================================================");
		
		return "myPage/deleteUser";
	}
	
	//ȸ��Ż�� ���� �������� �ݹ� ������
	@RequestMapping(value="/login/reauth/{social}/callback", method=RequestMethod.GET)
	public String userDeleteCallback(HttpSession session, @PathVariable String social, 
			@RequestParam String code, @RequestParam String state) throws Exception {
		log.info("\n����� ȸ��Ż��� ������ �ݹ� ������");
		boolean isComplete = false;	//ȸ��Ż�� socialLogin Ŭ�������� �����ߴ����� ��� ���� boolean�� ����
		
		//�̰͵� īī���� ���̹��� ȸ�� Ż�� ����� �ٸ� ���̱� ������ �켱 if������ �ۼ�.
		if (social.equals("naver")) {
			isComplete = naverLogin.compareAccessToken(session, code, state);
			log.info("isComplete : " + isComplete);
		}
		//�������� �������Ƿ� session���� reauthState ����.
		session.removeAttribute("reauthState");
		
		if (isComplete==true) {	//Ż�� ó��(��ū�� ����)�� ����������
			log.info("deleteToken ������ �Ϸ��߽��ϴ�.");
			log.info("�ش� ���� �����͸� DB���� �����մϴ�.");
			//��ū�� ���� �Ǿ����Ƿ� customer ���̺��� �ش� customerCode�� ���� ������ ����
			customerService.deleteCustomer((long) session.getAttribute("customerCode"));
			session.invalidate();	//ȸ�� Ż�� ������ �Ϸ�Ǿ����Ƿ� session�� ��ȿȭ.
			
			return "redirect:/";
		}
		else {
			//ȸ��Ż�� ���н� ������������ �̵�.
			return "redirect:/login/userDelete/error";
		}
	}
	
	//ȸ��Ż�� ������ �������� �� �̵��ϴ� ����������
		//������ �ۼ� �� POST�� ����
	@RequestMapping(value="/login/userDelete/error")
	public String userDeleteError() {
	
		return "myPage/userDeleteError";
	}
	
	//�α׾ƿ� ������
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		log.info("�α׾ƿ� �մϴ�!");
		session.invalidate();	//�α׾ƿ� �Ǿ����Ƿ� ���� ����.
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/addressapi/popup")
	public String addressApiPopup() {
		
		return "jusoPopup";
	}
}
