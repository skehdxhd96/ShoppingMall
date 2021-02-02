package org.zerock.domain;

import lombok.Data;

@Data
public class CustomerVO {
	private int customerCode;
	private String customerEmail;
	private String customerAddress;
	private String customerPhone;
	private String customerName;
	private int customerType;
	private String socialId;
	private int customerPoint;
	private String signupDate;
	private String socialType;
	private String companyName;
	private String companyPhone;
}
