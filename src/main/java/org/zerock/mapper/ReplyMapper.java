package org.zerock.mapper;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public int CustomerReply(Map map);
	
	public int OrderStatusIsDone(Map map);
	
	public int getOrderCode(Map map);
}
