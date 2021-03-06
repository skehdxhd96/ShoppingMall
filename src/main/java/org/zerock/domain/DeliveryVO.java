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
	
	public DeliveryVO(int deliveryZipcode, String shippingAddress, String requests, String deliverPhone, int orderCode, String recipient) {
		this.deliveryZipcode = deliveryZipcode;
		this.shippingAddress = shippingAddress;
		this.requests = requests;
		this.deliverPhone = deliverPhone;
		this.orderCode = orderCode;
		this.recipient = recipient;
	}
}
