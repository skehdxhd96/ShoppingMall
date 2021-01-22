package org.zerock.domain;

import lombok.Data;

@Data
public class SocialDetailVO {
	int customerCode;
	String accessToken;
	String refreshToken;
	String expiredDate;
}
