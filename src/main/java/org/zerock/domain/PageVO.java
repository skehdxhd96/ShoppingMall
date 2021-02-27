package org.zerock.domain;

import lombok.Data;

@Data
public class PageVO {
	private int page;
	private int cntPerPage;
	private int offset;
	
	public PageVO() {};
	
	public PageVO(int page, int cntPerPage) {
		this.page = page;
		this.cntPerPage = cntPerPage;
		if (this.page==0) {
			this.offset = 0;
		} else {
			this.offset = page*cntPerPage; 
		}
	}
}
