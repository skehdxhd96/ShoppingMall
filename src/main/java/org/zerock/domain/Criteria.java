package org.zerock.domain;

import lombok.Data;

@Data
public class Criteria {

	private int pageNum; // 페이지 번호
	private int amount; // 한 페이지당 몇개의 데이터
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
