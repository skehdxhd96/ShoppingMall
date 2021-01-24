package org.zerock.service;

import org.zerock.domain.CustomerVO;

public interface CustomerService {
	public CustomerVO isCustomer(String socialId);
}
