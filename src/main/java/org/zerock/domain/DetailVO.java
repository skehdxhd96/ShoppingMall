package org.zerock.domain;

import lombok.Data;

@Data
public class DetailVO {

	private int product_code;
	private String product_name; 
	private String product_manufacturer;
	private String product_seller;
	private String customerName;
	private int product_price;
	private int product_stock;
	private int product_point;
	private String product_score;
	private int category_code;
	private String category_name;
	private String image_url;
	private String thumbnail_url;
}
