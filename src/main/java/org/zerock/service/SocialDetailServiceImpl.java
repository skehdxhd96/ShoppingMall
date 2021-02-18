package org.zerock.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.SocialDetailVO;
import org.zerock.mapper.SocialDetailMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
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
		log.info("\nInsert new data in social_detail");
		log.info("customer_code : " + socialDetailVO.getCustomerCode());
		log.info("accessToken : " + socialDetailVO.getAccessToken());
		log.info("refreshToken : " + socialDetailVO.getRefreshToken());
		log.info("expiredDate : " + socialDetailVO.getExpiredDate());
		
		scm.insertTokenData(socialDetailVO);
	}

	@Override
	public void updateTokenData(SocialDetailVO socialDetailVO) {
		log.info("socialDetail 테이블의 데이터를 업데이트 합니다!");
		log.info("customer_code:" + socialDetailVO.getCustomerCode());
		
		scm.updateTokenData(socialDetailVO);
	}

}
