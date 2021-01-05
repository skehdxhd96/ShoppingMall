package org.zerock.service;

import java.util.List;

import org.zerock.domain.CategoryVO;
import org.zerock.domain.ProductVO;

public interface ProductService {

	public List<CategoryVO> getCategory();
	
	public void register(ProductVO p);
	
	public List<ProductVO> getList();
}
