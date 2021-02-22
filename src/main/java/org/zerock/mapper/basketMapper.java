package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.GetOrderInfoVO;
import org.zerock.domain.basketPageVO;
import org.zerock.domain.basketVO;

public interface basketMapper {

	public List<basketVO> getList(@Param("cri") Criteria cri, @Param("customerCode") Long customerCode);
	
	public int getBasketProduct(basketVO b);
	
	public basketPageVO getListPage(Criteria cri, Long customer_code);
	
	public int getBasketCount(Long customer_code);
	
	public CodeVO getPK(@Param("product_code") int product_code, @Param("customer_code") Long customer_code);
	
	public int deleteBasket(HashMap<String, Object> hm);
	
	public int removeBasket(@Param("customer_code") Long customer_code, @Param("product_code") int product_code);
	
	public List<GetOrderInfoVO> getOrderInfo(@Param("customer_code") Long customer_code, Integer[] CheckedArray);
	
	public int getTotalPrice(@Param("customer_code") Long customer_code, Integer[] CheckedArray);
}
