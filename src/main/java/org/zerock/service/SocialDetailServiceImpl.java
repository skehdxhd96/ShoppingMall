package org.zerock.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.SocialDetailVO;
import org.zerock.mapper.SocialDetailMapper;

@Service
public class SocialDetailServiceImpl implements SocialDetailService {
	@Inject
	SocialDetailMapper scm;
	
	@Override
	public SocialDetailVO findBySocialDetail(long customerCode) {
		SocialDetailVO socialDetailVO = scm.findBySocialDetail(customerCode);
		
		return socialDetailVO;
	}

	@Override
	public void insertTokenData(SocialDetailVO socialDetailVO) {
		System.out.println("\nInsert new data in social_detail");
		System.out.println("customer_code : " + socialDetailVO.getCustomerCode());
		System.out.println("accessToken : " + socialDetailVO.getAccessToken());
		System.out.println("refreshToken : " + socialDetailVO.getRefreshToken());
		System.out.println("expiredDate : " + socialDetailVO.getExpiredDate());
		
		scm.insertTokenData(socialDetailVO);
	}

	@Override
	public void updateTokenData(SocialDetailVO socialDetailVO) {
		System.out.println("socialDetail 테이블의 데이터를 업데이트 합니다!");
		System.out.println("customer_code:" + socialDetailVO.getCustomerCode());
		
		scm.updateTokenData(socialDetailVO);
	}

}
