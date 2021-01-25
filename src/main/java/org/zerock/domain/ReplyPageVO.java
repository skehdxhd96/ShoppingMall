package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

//댓글 전체가 아니라 댓글 목록과 전체 댓글의 수를 같이 전달하기 위한 VO

@Data
@AllArgsConstructor
public class ReplyPageVO {

	private int ReplyCount;
	private List<ReplyVO> list;
	
}
