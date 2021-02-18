package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {

	public int insert(ReplyVO vo);
	
	public ReplyVO read(int review_code);
	
	public int delete(int review_code);
	
	public int update(ReplyVO r);
	
	public List<ReplyVO> getListWIthPaging(@Param("cri") Criteria cri, @Param("product_code") int product_code);
	
	public int getCountByProductCode(int product_code);
	
	public Integer getOrderDetailCode(@Param("customer_code") Long customer_code, @Param("product_code") int product_code);
	
	public List<HashMap<String, Integer>> getReviewList(@Param("customer_code") Long customer_code, @Param("product_code") int product_code);
	
	public Integer getScore(int product_code);

}
