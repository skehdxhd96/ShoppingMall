package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.basketVO;
import org.zerock.mapper.ReplyMapper;
import org.zerock.mapper.basketMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class basketServiceImpl implements basketService{
	
	@Setter(onMethod_ = @Autowired)
	private basketMapper bm;
	
	@Override
	public List<basketVO> getList(Criteria cri, Long customer_code) {
		
		return bm.getList(cri, customer_code);
	}

	@Override
	public int getBasketProduct(basketVO b) {
		
		return bm.getBasketProduct(b);
	}
	
	@Override
	public CodeVO getPK(int product_code, long customer_code) {
		
		return bm.getPK(product_code, customer_code);
	}
}
