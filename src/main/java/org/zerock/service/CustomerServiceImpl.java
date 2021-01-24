package org.zerock.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.CustomerVO;
import org.zerock.mapper.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Inject
	private CustomerMapper cm;
	
	public CustomerVO isCustomer(String socialId)   {
		CustomerVO customerVO = cm.isCustomer(socialId);
		
		return customerVO;
	}
}
