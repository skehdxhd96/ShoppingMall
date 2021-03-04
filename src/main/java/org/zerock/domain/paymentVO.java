package org.zerock.domain;

import lombok.Data;

@Data
public class paymentVO {

	private String payment_code;
	private String payment_method;
	private int used_point;
	private int saved_point;
	private boolean point_check;
	private int total_payment_amount;
	private String payment_status;
	private int order_code;
}
