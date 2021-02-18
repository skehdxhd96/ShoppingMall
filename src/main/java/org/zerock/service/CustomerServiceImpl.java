package org.zerock.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.CustomerVO;
import org.zerock.mapper.CustomerMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CustomerServiceImpl implements CustomerService {
	@Inject
	private CustomerMapper cm;

	@Override
	public void insertBuyer(CustomerVO customer) {
		cm.insertBuyer(customer);
		log.info("구매자 insert 성공!");
	}

	@Override
	public HashMap<String, Object> getLoginInfo(String socialId) {
		HashMap<String, Object> loginInfo = cm.getLoginInfo(socialId);
		
		return loginInfo;
	}

	@Override
	public void insertSeller(CustomerVO customer) {
		cm.insertSeller(customer);
		log.info("판매자 insert 성공!");
	}

	@Override
	public String getCustomerName(long customerCode) {
		String customerName = cm.getCustomerName(customerCode);
		
		return customerName;
	}
	
	@Override
	public String getCompanyName(long customerCode) {
		
		String CompanyName = cm.getCompanyName(customerCode);
		
		return CompanyName;
	}

	@Override
	public CustomerVO getBuyerProfile(long customerCode) {
		CustomerVO buyerProfile = cm.getBuyerProfile(customerCode);
		
		return buyerProfile;
	}

	@Override
	public CustomerVO getSellerProfile(long customerCode) {
		CustomerVO sellerProfile = cm.getSellerProfile(customerCode);
		
		return sellerProfile;
	}

	@Override
	public String getSocialId(long customerCode) {
		String socialId = cm.getSocialId(customerCode);
		
		return socialId;
	}

	@Override
	public void deleteCustomer(long customerCode) {
		cm.deleteCustomer(customerCode);
		log.info("customerCode : " + customerCode + " 회원 삭제를 완료했습니다.");
	}

	@Override
	public void updateBuyer(CustomerVO updateCustomer) {
		cm.updateBuyer(updateCustomer);
		log.info("customerCode : " + updateCustomer.getCustomerCode() + "구매자 회원 수정 완료");
	}

	@Override
	public void updateSeller(CustomerVO updateCustomer) {
		cm.updateSeller(updateCustomer);
		log.info("customerCode : " + updateCustomer.getCustomerCode() + "판매자 회원 수정 완료");
	}

	@Override
	public int updatePoint(long customerCode, long totalPoint) {
		HashMap<String, Object> pointHm = new HashMap<String, Object>();
		
		pointHm.put("customerCode", customerCode);
		pointHm.put("totalPoint", totalPoint);
		
		return cm.updatePoint(pointHm);
	}
}
