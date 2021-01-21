package org.zerock.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.DetailVO;
import org.zerock.domain.ProductVO;
import org.zerock.service.ProductServiceImpl;
import org.zerock.utils.UploadFileUtils;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/shop/")
@Log4j
public class MainController {
	@Resource
	private ProductServiceImpl pm;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // servlet-context������ �־��

	@RequestMapping("/")
	public String toMainPage() {
		
		return "/mainPage";
	}
	
	//ī�װ��� ��ǰ ����Ʈ ������
	   @RequestMapping("/ProductList/{categoryCode}")
	   public String productByCategory(@PathVariable("categoryCode") int categoryCode, Model model) {
		 //ī�װ� �׸�
	      List<CategoryVO> categoryVOList = pm.getCategory();
	      model.addAttribute("categories", categoryVOList);
	      
	      //��������
	      int pageNum = (int) (pm.getCount(categoryCode)/6)+1;
	      model.addAttribute("pageNum", pageNum);
	      
	    //getListByCategory ���������� �ؽ���
	      HashMap<String, Object> parameterHm = new HashMap<String, Object>();
	      parameterHm.put("categoryCode", categoryCode);
	      parameterHm.put("startIdx", 0);
	      
	    //��ǰ����Ʈ -1������
	      List<ProductVO> productVOList = pm.getListByCategory(parameterHm);
	      model.addAttribute("products", productVOList);
	      
	      return "/ProductList";
	   }
	
	 //����¡��ư, ���������� ��ư ajax �����۾�
	@RequestMapping(value="/ProductList/paging", method=RequestMethod.POST)
	@ResponseBody
	public List<ProductVO> productPaging(@RequestBody HashMap<String, Object> dataTransfer) {
		List<ProductVO> productVOList = pm.getListByCategory(dataTransfer);
		
		return productVOList;
	}
	
	//���������� ��ư ajax �����۾�
	@RequestMapping(value="/ProductList/nextButton", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> nextButton(@RequestBody HashMap<String, Object> dataTransfer) {
		//ajax success�� ������ ������
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		//�ش� �������� ������ ��ǰ������ ����Ʈ
		List<ProductVO> productVOList = pm.getListByCategory(dataTransfer);
		hm.put("productList", productVOList);
		System.out.println(dataTransfer.get("categoryCode").getClass().getName());
		
		//ī�װ��ڵ�
		int categoryCode = (int) dataTransfer.get("categoryCode");
		//�� ������
		int totalPage = (int) (pm.getCount(categoryCode)/6)+1;
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
	public String toUploadPage(ProductVO p, MultipartFile file) throws Exception{
		
		//�̹��� ��� �޼ҵ�
		String imgUploadPath = uploadPath + File.separator + "imgUpload"; //resources/imgUpload
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath); //����� �������
		String fileName = null;

		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
		 fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath); 
		} else {
		 fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}
		
		p.setImage_url(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		p.setThumbnail_url(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		//�̹��� ��� �޼ҵ� end
		
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
		
		//������ �غ�
		DetailVO d = pm.getById(product_code);
		model.addAttribute("ModifyProduct", d);
		
		List<CategoryVO> category = null;
		category = pm.getCategory();
		model.addAttribute("category", JSONArray.fromObject(category));
		
		return "/ProductModify";
	}
	
	@PostMapping("/ProductModify/{product_code}")
	public String toModifyPage(@PathVariable("product_code") int product_code, ProductVO p) {
		
		//��������
		pm.ProductModify(p);
		
		return "redirect:/ProductList/1";
	}
	
	@PostMapping("/Delete")
	public String toDelete(int product_code) {
		
		pm.ProductDelete(product_code);
		
		return "redirect:/ProductList/1"; //�����������ؾ���
	}
}