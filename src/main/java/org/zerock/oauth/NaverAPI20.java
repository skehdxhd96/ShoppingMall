package org.zerock.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverAPI20 extends DefaultApi20 {
	private NaverAPI20() {
		
	}
	
	private static class InstanceHolder {
		public static final NaverAPI20 INSTANCE = new NaverAPI20();
	}
	
	public static NaverAPI20 instance() {
		return InstanceHolder.INSTANCE;
	}
	
	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		return "https://nid.naver.com/oauth2.0/authorize";
	}
}
