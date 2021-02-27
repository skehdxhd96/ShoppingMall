package org.zerock.domain;

import lombok.Data;

@Data
public class OrderVO {
	private int orderCode;
	private int totalOrderPrice;
	private String orderDate;
	private String orderStatus;
	private long customerCode;
	
	public OrderVO() {
		
	}
	public OrderVO(int totalOrderPrice, long customerCode) {
		this.totalOrderPrice = totalOrderPrice;
		this.customerCode = customerCode;
	}
	public OrderVO(String orderDate, String orderStatus, int totalOrderPrice) {
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.totalOrderPrice = totalOrderPrice;
	}
}
