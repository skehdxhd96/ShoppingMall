package org.zerock.domain;

import lombok.Data;

@Data
public class CustomerVO {
	int customerCode;
	String customerEmail;
	String customerAddress;
	String customerPhone;
	String customerName;
	int customerType;
	String socialId;
	int customerPoint;
	String signupDate;
	String socialType;
	String companyName;
	String companyPhone;
}
