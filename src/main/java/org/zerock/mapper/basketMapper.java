package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageVO;
import org.zerock.domain.basketPageVO;
import org.zerock.domain.basketVO;

public interface basketMapper {

	public List<basketVO> getList(@Param("cri") Criteria cri, @Param("customerCode") Long customerCode);
	
	public int getBasketProduct(basketVO b);
	
	public basketPageVO getListPage(Criteria cri, Long customer_code);
	
	public int getBasketCount(Long customer_code);
	
	public CodeVO getPK(@Param("product_code") int product_code, @Param("customer_code") Long customer_code);
	
	public int updateBasket(basketVO b);
	
	public int deleteBasket(HashMap<String, Object> hm);
}
