package org.zerock.domain;

import lombok.Data;

@Data
public class OrderDetailVO {
	private int orderDetailCode;
	private int orderCode;
	private int productCode;
	private int productQuantity;
	
	public OrderDetailVO(int orderCode, int productCode, int productQuantity) {
		this.orderCode = orderCode;
		this.productCode = productCode;
		this.productQuantity = productQuantity;
	}
}
