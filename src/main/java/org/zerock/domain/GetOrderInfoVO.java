package org.zerock.domain;

import lombok.Data;

@Data
public class GetOrderInfoVO {

	private int product_code;
	private int product_quantity;
	private int product_price;
}
