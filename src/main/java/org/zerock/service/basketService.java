package org.zerock.service;

import java.util.List;

import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.basketVO;

public interface basketService {
	
	public List<basketVO> getList(Criteria cri, Long customer_code);

	public int getBasketProduct(basketVO b);
	
	public CodeVO getPK(int product_code, long customer_code);
}
