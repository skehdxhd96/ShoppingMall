package org.zerock.domain;

import lombok.Data;

@Data
public class PageDTO {
	private int page;
	private int cntPerPage;
	private int offset;
	
	public PageDTO() {}
	
	public PageDTO(int page, int cntPerPage) {
		this.page = page;
		this.cntPerPage = cntPerPage;
		if (this.page==1) {
			this.offset = 0;
		} else {
			this.offset = (page-1)*cntPerPage; 
		}
	}
}