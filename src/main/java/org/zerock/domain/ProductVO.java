package org.zerock.domain;

import lombok.Data;

@Data
public class ProductVO {
	private int product_code;
	private String product_name; 
	private String product_manufacturer;
	private String product_seller; //�����Ұ�
	private int product_price;
	private int product_stock;
	private int product_point; //�����Ұ�
	private String product_score; // �����Ұ�
	private int category_code;
	private String image_url;
	private String thumbnail_url;
}