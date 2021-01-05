package org.zerock.domain;

import lombok.Data;

@Data
public class ProductVO {

	private int product_code;
	private String product_name;
	private String product_manufacturer;
	private String product_seller;
	private int product_price;
	private int product_stock;
	private int product_point;
	private int product_score;
	private int category_code;
}
