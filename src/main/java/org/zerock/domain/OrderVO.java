package org.zerock.domain;

import lombok.Data;

@Data
public class OrderVO {
	private int orderCode;
	private int totalOrderPrice;
	private String orderDate;
	private String orderStatus;
}
