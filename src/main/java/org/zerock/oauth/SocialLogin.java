package org.zerock.oauth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.zerock.domain.CustomerVO;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;


@Service
public class SocialLogin {
	private OAuth20Service oauth20Service;
	private String profileUrl;
	private String socialType;
	
	public SocialLogin() {
		
	}
	
	public SocialLogin(SocialValue social, HttpSession session) {
		String oauthState = generateState();
		session.setAttribute("oauthState", oauthState);
		
		this.oauth20Service = new ServiceBuilder()
				.apiKey(social.getClientId())
				.apiSecret(social.getClientSecret())
				.callback(social.getCallbackUrl())
				.state(oauthState)
				.build(social.getApi20Instance());
		
		this.profileUrl = social.getProfileUrl();
		this.socialType = social.getSocialType();
	}

	//위조 방지용 상태 토큰(state token) 생성
	public String generateState() {
		
		return UUID.randomUUID().toString();
	}

	public String getAuthorizationUrl() {
		// 인증 url 리턴
		return this.oauth20Service.getAuthorizationUrl();
	}
	
	//accessToken 가져오기
	public OAuth2AccessToken getAccessToken(String code, String state, HttpSession session) throws Exception {
		if (state.equals(session.getAttribute("oauthState"))) {
			System.out.println("state, session의 oauthState 일치!");
			System.out.println("accessToken 전달!");
			OAuth2AccessToken accessToken = this.oauth20Service.getAccessToken(code);
			
			return accessToken;
		}
		
		System.out.println("ERROR!:state, session의 oauthState 불일치!");
		
		return null;
	}
	
	//프로필 정보 가져오기
	public CustomerVO getProfile(OAuth2AccessToken accessToken) throws Exception {
		Response response = null;
		OAuthRequest request = new OAuthRequest(Verb.POST, this.profileUrl, this.oauth20Service);
		
		this.oauth20Service.signRequest(accessToken, request);
		response = request.send();
		
		System.out.println("프로필 response 완료!");
		return profileParsing(response.getBody());
	}
	
	//profile 정보를 JSON 데이터로 변환해 필요한 데이터를 CustomerVO에 저장.
	private CustomerVO profileParsing(String profileBody) throws Exception {
		CustomerVO customer = new CustomerVO();
		JSONParser jsonParse = new JSONParser();
		
		//JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다. 
		JSONObject jsonObj = (JSONObject) jsonParse.parse(profileBody);
		jsonObj = (JSONObject) jsonObj.get("response");
		
		if (socialType.equals("naver")) {
			customer.setSocialType("naver");
			customer.setSocialId((String) jsonObj.get("id"));
			customer.setCustomerEmail((String) jsonObj.get("email"));
			customer.setCustomerPhone((String) jsonObj.get("mobile"));
			customer.setCustomerName((String) jsonObj.get("name"));
		}
		
		return customer;
	}
}
