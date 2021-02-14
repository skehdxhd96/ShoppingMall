package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.basketPageVO;
import org.zerock.domain.basketVO;

public interface basketService {
	
	public List<basketVO> getList(Criteria cri, Long customer_code);

	public int getBasketProduct(basketVO b);
	
	public basketPageVO getListPage(Criteria cri, Long customer_code);
	
	public int getBasketCount(Long customer_code);
	
	public CodeVO getPK(int product_code, long customer_code);
	
	public int updateBasket(basketVO b);
	
	public int deleteBasket(long customerCode, List<Integer> productCodes);
}
