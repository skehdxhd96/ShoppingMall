package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SocialDetailVO {
	private long customerCode;
	private String accessToken;
	private String refreshToken;
	private String expiredDate;
}
