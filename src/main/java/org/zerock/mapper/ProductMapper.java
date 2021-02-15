package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.CategoryVO;
import org.zerock.domain.DetailVO;
import org.zerock.domain.ProductVO;

public interface ProductMapper {

	public List<CategoryVO> getCategory();
	
	public void register(ProductVO p);
	
	public List<ProductVO> getList();
	
	public DetailVO getById(int product_code);
	
	public int getCount(int categoryCode);
	
	public List<Integer> getPoints(List<Long> productCodes);
	
	public List<ProductVO> getListByCategory(HashMap<String, Object> parameterHm);
	
	public void ProductDelete(int product_code);
	
	public void ProductModify(ProductVO p);
	
	public int subStock(HashMap<String, Object> productInfoHm);
}