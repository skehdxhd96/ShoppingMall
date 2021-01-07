package org.zerock.domain;

import lombok.Data;

@Data
public class ProductVO {
	private int product_code;
	private String product_name; 
	private String product_manufacturer;
	private String product_seller; //수정불가
	private int product_price;
	private int product_stock;
	private int product_point; //수정불가
	private String product_score; // 수정불가
	private int category_code;
}
