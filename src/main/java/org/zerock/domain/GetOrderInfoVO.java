package org.zerock.domain;

import lombok.Data;

@Data
public class GetOrderInfoVO {

	private int productCode;
	private int productQuantity;
	private int productPrice;
}
