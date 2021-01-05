package org.zerock.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.CategoryVO;
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
	
	@RequestMapping("/ProductList")
	public String toProductList(Model model) {
		
		List<CategoryVO> categoryVOList = pm.getCategory();
		model.addAttribute("categories", categoryVOList);
		System.out.println(categoryVOList.get(0).getCategory_name());
		
		List<ProductVO> productVOList = pm.getList();
		System.out.println(productVOList.get(0).getProduct_price());
		model.addAttribute("products", productVOList);
		
		return "/ProductList";
	}
	
	@RequestMapping("/detail")
	public String toProductDetail() {
		
		return "/ProductDetail";
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
		
		return "redirect:/ProductList";
	}
}
