package org.zerock.service;

import java.util.HashMap;

import org.zerock.domain.CustomerVO;

public interface CustomerService {
	public HashMap<String, Object> getLoginInfo(String socialId);
	public String getCustomerName(long customerCode);
	public void insertBuyer(CustomerVO customer);
	public void insertSeller(CustomerVO customer);
	public String getCompanyName(long customerCode);
	public HashMap<String, Object> getBuyerProfile(long customerCode);
	public HashMap<String, Object> getSellerProfile(long customerCode);
}
