package org.zerock.service;

import org.zerock.domain.SocialDetailVO;

public interface SocialDetailService {
	public SocialDetailVO findBySocialDetail(long customerCode);
	public void insertTokenData(SocialDetailVO socialDetailVO);
	public void updateTokenData(SocialDetailVO socialDetailVO);
}
