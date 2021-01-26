package org.zerock.mapper;

import org.zerock.domain.SocialDetailVO;

public interface SocialDetailMapper {
	public SocialDetailVO findBySocialDetail(long customerCode);
	public void insertTokenData(SocialDetailVO socialDetailVO);
	public void updateTokenData(SocialDetailVO socialDetailVO);
}
