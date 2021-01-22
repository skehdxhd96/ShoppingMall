package org.zerock.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class SocialValue {
	private String socialType;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private DefaultApi20 api20Instance;
	private String profileUrl;
	
	public SocialValue(String socialType, String clientId, String clientSecret, String callbackUrl) {
		this.socialType = socialType;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.callbackUrl = callbackUrl;
		
		if (socialType.equals("naver")) {
			this.api20Instance = NaverAPI20.instance();
			this.profileUrl = "https://openapi.naver.com/v1/nid/me";
		}
	}
}
