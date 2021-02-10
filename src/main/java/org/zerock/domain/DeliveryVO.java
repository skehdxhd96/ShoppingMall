package org.zerock.domain;

import lombok.Data;

@Data
public class DeliveryVO {
	private int deliveryCode;
	private String shippingAddress;
	private String deliveryStatus;
	private String requests;
	private String deliverPhone;
	private int orderCode;
	
	public DeliveryVO(int orderCode) {
		this.orderCode = orderCode;
	}
}
