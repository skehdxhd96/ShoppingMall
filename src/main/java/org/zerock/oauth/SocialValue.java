package org.zerock.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class SocialValue {
	private String socialType;
	private String clientId;
	private String clientSecret;
	private String callbackUrl;
	private String reauthCallbackUrl;
	private DefaultApi20 api20Instance;
	private String profileUrl;
	private String deleteTokenUrl;
	
	public SocialValue(String socialType, String clientId, String clientSecret, String callbackUrl, String reauthCallbackUrl) {
		this.socialType = socialType;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.callbackUrl = callbackUrl;
		this.reauthCallbackUrl = reauthCallbackUrl;
		
		if (socialType.equals("naver")) {
			this.api20Instance = NaverAPI20.instance();
			this.profileUrl = "https://openapi.naver.com/v1/nid/me";
			this.deleteTokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete";
		}
	}
}
