package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.basketVO;

public interface basketService {
	
	public List<basketVO> getList(Criteria cri, Long customer_code);

}
