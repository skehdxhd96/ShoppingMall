package org.zerock.domain;

import lombok.Data;

@Data
public class CustomerVO {
	private long customerCode;
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
	private int zipcode;
	
	public CustomerVO() {
		
	}
	
	public CustomerVO(String customerName, String customerEmail, String customerPhone, String customerAddress) {
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
	}
	
	public CustomerVO(String customerName, String customerEmail, String customerPhone, String customerAddress, 
			String companyName, String companyPhone) {
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.companyName = companyName;
		this.companyPhone = companyPhone;
	}
}
