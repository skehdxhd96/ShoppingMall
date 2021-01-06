package org.zerock.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.ProductVO;
import org.zerock.mapper.ProductMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductMapper pm;
	
	@Override
	public List<CategoryVO> getCategory() {
		System.out.println("ProductServiceImpl.getCategory()");
		
		return pm.getCategory();
	}
	
	@Override
	public void register(ProductVO p) {
		
//		log.info("register is done");
		
		pm.register(p);
	}
	
	@Override
	public List<ProductVO> getList() {
		
		return pm.getList();
	}
}
