package org.zerock.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryVO {
	private int deliveryCode;
	private int deliveryZipcode;
	private String shippingAddress;
	private String deliveryStatus;
	private String requests;
	private String deliverPhone;
	private int orderCode;
	private String recipient;
	
	public DeliveryVO(int orderCode) {
		this.orderCode = orderCode;
	}
	
	public DeliveryVO(String shippingAddress, String recipient) {
		this.shippingAddress = shippingAddress;
		this.recipient = recipient;
	}
}
