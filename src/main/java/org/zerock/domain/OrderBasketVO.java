package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderBasketVO {
	private int orderCode;
	private long customerCode;
	private int productCode;
	private int productQuantity;
}
