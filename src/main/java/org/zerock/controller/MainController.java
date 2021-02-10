package org.zerock.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.CodeVO;
import org.zerock.domain.DetailVO;
import org.zerock.domain.ProductVO;
import org.zerock.service.CustomerServiceImpl;
import org.zerock.service.ProductServiceImpl;
import org.zerock.service.ReplyService;
import org.zerock.service.basketService;
import org.zerock.utils.GetScoreUtils;
import org.zerock.utils.UploadFileUtils;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;

@Controller
@Log4j
public class MainController {
	@Resource
	private ProductServiceImpl pm;
	
	@Resource
	private basketService bm;
	
	@Resource
	private ReplyService rm;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // servlet-context占쏙옙占쏙옙占쏙옙 占쌍억옙占�
	
	@Inject
	CustomerServiceImpl customerService;

	@RequestMapping("/")
	public String toMainPage(HttpSession session, Model model) {
		String customerName = "";
		Long customerCode = (Long) session.getAttribute("customerCode");
		
		if (customerCode!=null) {
			customerName = customerService.getCustomerName(customerCode);
		}
		model.addAttribute("customerName", customerName);
		
		return "mainPage";
	}
	
	//카테고리별 상품 리스트 페이지
	   @RequestMapping("/ProductList/{categoryCode}")
	   public String productByCategory(@PathVariable("categoryCode") int categoryCode, Model model) {

		 //카테고리 항목
	      List<CategoryVO> categoryVOList = pm.getCategory();
	      model.addAttribute("categories", categoryVOList);
	      
	      //총페이지
	      int pageNum = (int) (pm.getCount(categoryCode)/6)+1;
	      model.addAttribute("pageNum", pageNum);
	      
	    //getListByCategory 다중쿼리문 해쉬맵
	      HashMap<String, Object> parameterHm = new HashMap<String, Object>();
	      parameterHm.put("categoryCode", categoryCode);
	      parameterHm.put("startIdx", 0);
	      
	    //상품리스트 -1페이지
	      List<ProductVO> productVOList = pm.getListByCategory(parameterHm);
	      model.addAttribute("products", productVOList);
	      
	      return "/ProductList";
	   }
	
	//페이징버튼, 이전페이지 버튼 ajax 서버작업
	@RequestMapping(value="/ProductList/paging", method=RequestMethod.POST)
	@ResponseBody
	public List<ProductVO> productPaging(@RequestBody HashMap<String, Object> dataTransfer) {
		List<ProductVO> productVOList = pm.getListByCategory(dataTransfer);
		
		return productVOList;
	}
	
	//다음페이지 버튼 ajax 서버작업
	@RequestMapping(value="/ProductList/nextButton", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> nextButton(@RequestBody HashMap<String, Object> dataTransfer) {
		//ajax success로 전달한 데이터
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		//해당 페이지에 전달할 상품데이터 리스트
		List<ProductVO> productVOList = pm.getListByCategory(dataTransfer);
		hm.put("productList", productVOList);
		System.out.println(dataTransfer.get("categoryCode").getClass().getName());
		
		//카테고리코드
		int categoryCode = (int) dataTransfer.get("categoryCode");
		//총 페이지
		int totalPage = (int) (pm.getCount(categoryCode)/6)+1;
		hm.put("totalPage", totalPage);
		
		return hm;
	}
	
	@GetMapping("/ProductUpload")
	public void toUploadPage(Model model, HttpSession session) {
		
		String CompanyName = "";
		String customerName = "";
		Long customerCode = (Long) session.getAttribute("customerCode");
		
		if (customerCode!=null) {
			CompanyName = customerService.getCompanyName(customerCode);
			customerName = customerService.getCustomerName(customerCode);
		}
		
		List<CategoryVO> category = null;
		category = pm.getCategory();
		model.addAttribute("category", JSONArray.fromObject(category));
		model.addAttribute("CompanyName", CompanyName);
		model.addAttribute("customerName", customerName);
	}
	
	@PostMapping("/ProductUpload")
	public String toUploadPage(ProductVO p, MultipartFile file) throws Exception{
		
		//이미지 등록 메소드
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath); //년월일 폴더경로
		String fileName = null;

		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
		 fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath); 
		} else {
		 fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}
		
		p.setImage_url(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		p.setThumbnail_url(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		//이미지 등록 메소드 end
		
		pm.register(p);
		
		return "redirect:/ProductList/1";
	}
	
	@GetMapping("/ProductDetail/{product_code}")
	public String toDetail(@PathVariable("product_code") int product_code ,Model model, HttpSession session) {
		
		String CompanyName = "";
		String customerName = "";
		Long customerCode = (Long) session.getAttribute("customerCode");
		Map map = new HashMap<>();
		ArrayList<Integer> score = rm.getScore(product_code);
		double AverageScore = GetScoreUtils.getScore(score);
		if (customerCode!=null) {
			CompanyName = customerService.getCompanyName(customerCode);
			customerName = customerService.getCustomerName(customerCode);
			map.put("product_code", product_code);
			map.put("customer_code", customerCode);
			
			model.addAttribute("CompanyName", CompanyName);
			model.addAttribute("customerName", customerName);
			
			model.addAttribute("CustomerReply", rm.CustomerReply(map));
			model.addAttribute("OrderCodeIsDone", rm.OrderStatusIsDone(map));
			model.addAttribute("getOrderCode", rm.getOrderCode(map));
		}
		
		DetailVO d = pm.getById(product_code);
		model.addAttribute("ProductById", d);
		model.addAttribute("AverageScore", AverageScore);
		return "/ProductDetail";
	}
	
	@GetMapping("/ProductModify/{product_code}")
	public String toModifyPage(@PathVariable("product_code") int product_code, Model model) {
		
		//占쏙옙占쏙옙占쏙옙 占쌔븝옙
		DetailVO d = pm.getById(product_code);
		model.addAttribute("ModifyProduct", d);
		
		List<CategoryVO> category = null;
		category = pm.getCategory();
		model.addAttribute("category", JSONArray.fromObject(category));
		
		return "/ProductModify";
	}
	
	@PostMapping("/ProductModify/{product_code}")
	public String toModifyPage(@PathVariable("product_code") int product_code, ProductVO p) {
		
		//占쏙옙占쏙옙占쏙옙占쏙옙
		pm.ProductModify(p);
		
		return "redirect:/ProductList/1";
	}
	
	@PostMapping("/Delete")
	public String toDelete(int product_code) {
		
		pm.ProductDelete(product_code);
		
		return "redirect:/ProductList/1"; //占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙占쌔억옙占쏙옙
	}
	
	//마이페이지-마이페이지 초기화면은 주문목록
	@RequestMapping("/myPage/order/list")
	public String orderList() {
		
		return "myPage/orderList";
	}
	
	@RequestMapping("/myPage/basket")
	public String basket() {
		
		return "myPage/basket";
	}
	
	//접근 불가능 오류 페이지
	@RequestMapping("/error/accessDenied")
	public String accessDenied() {
		
		return "accessDenied";
	}
}