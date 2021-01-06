package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.CategoryVO;
import org.zerock.domain.ProductVO;
import org.zerock.mapper.ProductMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ProductServiceImpl implements ProductService{

	@Setter(onMethod_ = @Autowired)
	private ProductMapper pm;
	
	@Override
	public List<CategoryVO> getCategory() {
		
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
	
	@Override
	public int getCount(int categoryCode) {
		
		return pm.getCount(categoryCode);
	}
	
	@Override
	public List<ProductVO> getListByCategory(HashMap parameterHm) {
		
		return pm.getListByCategory(parameterHm);
	}
}
