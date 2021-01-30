package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageVO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {

	public int insert(ReplyVO r);
	
	public ReplyVO read(int review_code);
	
	public int update(ReplyVO r);
	
	public int delete(int review_code);
	
	public List<ReplyVO> getListWithPaging(Criteria cri, int product_code);
	
	public ReplyPageVO getListPage(Criteria cri, int product_code);
	
	public int ReplyAuthorityCustomer(int product_code);
	
	public int ReplyAuthorityProduct(long customer_code);
	
	public int getOrderCode(long customerCode);
	
	public String ReplyAuthorityStatus(int order_code);
}
