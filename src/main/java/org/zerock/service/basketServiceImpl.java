package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.zerock.domain.CodeVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.basketPageVO;
import org.zerock.domain.basketVO;
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
		int result = 0;
		
		//중복키 오류나면 insert->update로
		try {
			result = bm.getBasketProduct(b);
		} catch (DuplicateKeyException e) {
			result = bm.updateBasket(b);
		}
		
		return result;
	}
	
	@Override
	public basketPageVO getListPage(Criteria cri, Long customer_code) {
		
		return new basketPageVO(bm.getBasketCount(customer_code), bm.getList(cri, customer_code));
	}
	
	@Override
	public int getBasketCount(Long customer_code) {
		
		return bm.getBasketCount(customer_code);
	}
	
	@Override
	public CodeVO getPK(int product_code, long customer_code) {
		
		return bm.getPK(product_code, customer_code);
	}

	@Override
	public int updateBasket(basketVO b) {
		
		return bm.updateBasket(b);
	}
}
