package org.zerock.domain;

import lombok.Data;

@Data
public class DetailVO {

	private int product_code;
	private String product_name; 
	private String product_manufacturer; // 제조 회사
	private String product_seller; //판매 회사
	private String customerName; // 판매자이름
	private int product_price;
	private int product_stock;
	private int product_point;
	private String product_score;
	private int category_code;
	private String category_name;
	private String image_url;
	private String thumbnail_url;
}
