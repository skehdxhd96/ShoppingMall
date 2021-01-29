package org.zerock.mapper;

import java.util.HashMap;

import org.zerock.domain.CustomerVO;

public interface CustomerMapper {
	public HashMap<String, Object> getLoginInfo(String socialId);
	public String getCustomerName(long customerCode);
	public void insertBuyer(CustomerVO customer);
	public void insertSeller(CustomerVO customer);
	public String getCompanyName(long customerCode);
}
