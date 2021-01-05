package org.zerock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.ProductVO;
import org.zerock.mapper.ProductMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;

@Controller
@Log4j
@AllArgsConstructor
public class MainController {
	
	private ProductMapper pm;

	@RequestMapping("/")
	public String toMainPage() {
		
		return "/mainPage";
	}
	
	@RequestMapping("/ProductList")
	public void toProductList(Model model) {
		
		model.addAttribute("product", pm.getList());
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
