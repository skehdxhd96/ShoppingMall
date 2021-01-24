package org.zerock.mapper;

import org.zerock.domain.CustomerVO;

public interface CustomerMapper {
	public CustomerVO isCustomer(String socialId);
}
