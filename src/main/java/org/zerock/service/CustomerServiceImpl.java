package org.zerock.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.CustomerVO;
import org.zerock.mapper.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Inject
	private CustomerMapper cm;

	@Override
	public void insertBuyer(CustomerVO customer) {
		cm.insertBuyer(customer);
		System.out.println("구매자 insert 완료!");
	}

	@Override
	public HashMap<String, Object> getLoginInfo(String socialId) {
		HashMap<String, Object> loginInfo = cm.getLoginInfo(socialId);
		
		return loginInfo;
	}

	@Override
	public void insertSeller(CustomerVO customer) {
		cm.insertSeller(customer);
		System.out.println("판매자 insert 완료!");
	}

	@Override
	public String getCustomerName(long customerCode) {
		String customerName = cm.getCustomerName(customerCode);
		
		return customerName;
	}
}
