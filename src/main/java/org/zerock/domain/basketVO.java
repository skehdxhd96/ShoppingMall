package org.zerock.domain;

import lombok.Data;

@Data
public class basketVO {
	private int product_code;
	private String product_name;
	private int product_price;
	private String product_manufacturer;
	private long customer_code;
	private int product_quantity;
	
	public basketVO(int product_code, long customer_code, int product_quantity) {
		this.product_code = product_code;
		this.customer_code = customer_code;
		this.product_quantity = product_quantity;
	}
}
