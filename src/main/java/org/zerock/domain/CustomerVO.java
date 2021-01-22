package org.zerock.domain;

import lombok.Data;

@Data
public class CustomerVO {
	int customerCode;
	String customerEmail;
	String customerAddress;
	String customerPhone;
	String customerName;
	int customerCheck;
	String socialId;
	int customerPoint;
	String signupDate;
	String socialType;
}
