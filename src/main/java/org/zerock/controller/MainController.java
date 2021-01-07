package org.zerock.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.DetailVO;
import org.zerock.domain.ProductVO;
import org.zerock.service.ProductServiceImpl;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;

@Controller
@Log4j
public class MainController {
	@Resource
	private ProductServiceImpl pm;

	@RequestMapping("/")
	public String toMainPage() {
		
		return "/mainPage";
	}
	
	//카테고리별 상품 리스트 페이지
	   @RequestMapping("/ProductList/{categoryCode}")
	   public String productByCategory(@PathVariable("categoryCode") int categoryCode, Model model) {
	      //카테고리 항목
	      List<CategoryVO> categoryVOList = pm.getCategory();
	      model.addAttribute("categories", categoryVOList);
	      
	      //총페이지
	      int pageNum = (int) Math.ceil(pm.getCount(categoryCode)/6);
	      model.addAttribute("pageNum", pageNum);
	      
	      //getListByCategory 다중쿼리문 해쉬맵
	      HashMap<String, Object> parameterHm = new HashMap<String, Object>();
	      parameterHm.put("categoryCode", categoryCode);
	      parameterHm.put("startIdx", 0);
	      
	      //상품리스트-1페이지
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
		
		//총 페이지
		int totalPage = (int) Math.ceil(pm.getCount((int) dataTransfer.get("categoryCode"))/6.0);
		hm.put("totalPage", totalPage);
		
		return hm;
	}
	
	@GetMapping("/ProductUpload")
	public void toUploadPage(Model model) {
		
		List<CategoryVO> category = null;
		category = pm.getCategory();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	@PostMapping("/ProductUpload")
	public String toUploadPage(ProductVO p) throws Exception{
		
		pm.register(p);
		
		return "redirect:/ProductList/1";
	}
	
	@GetMapping("/ProductDetail/{product_code}")
	public String toDetail(@PathVariable("product_code") int product_code ,Model model) {
		
		DetailVO d = pm.getById(product_code);
		model.addAttribute("ProductById", d);
		
		return "/ProductDetail";
	}
	
	@GetMapping("/ProductModify/{product_code}")
	public String toModifyPage(@PathVariable("product_code") int product_code, Model model) {
		
		//데이터 준비
		DetailVO d = pm.getById(product_code);
		model.addAttribute("ModifyProduct", d);
		
		List<CategoryVO> category = null;
		category = pm.getCategory();
		model.addAttribute("category", JSONArray.fromObject(category));
		
		return "/ProductModify";
	}
	
	@PostMapping("/ProductModify/{product_code}")
	public String toModifyPage(@PathVariable("product_code") int product_code, ProductVO p) {
		
		//수정시작
		pm.ProductModify(p);
		
		return "redirect:/ProductList/1";
	}
	
	@PostMapping("/Delete")
	public String toDelete(int product_code) {
		
		pm.ProductDelete(product_code);
		
		return "redirect:/ProductList/1"; //페이지조정해아함
	}
}
