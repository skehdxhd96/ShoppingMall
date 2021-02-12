package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private int review_code;
	private int order_detail_code;
	private Date review_date;
	private String review_comment;
	private int review_score;
	private int product_code;
	private int customer_code;
	private String customer_name;
}
