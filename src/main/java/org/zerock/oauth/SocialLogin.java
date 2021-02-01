package org.zerock.oauth;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.zerock.domain.CustomerVO;
import org.zerock.domain.SocialDetailVO;
import org.zerock.service.SocialDetailServiceImpl;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.RequiredArgsConstructor;

@Service
public class SocialLogin {
	private OAuth20Service oauth20Service;
	private String profileUrl;
	private String socialType;
	private SocialValue socialValue;

	public void updateTest(SocialValue social) {
		this.socialValue = social;
		this.profileUrl = social.getProfileUrl();
		this.socialType = social.getSocialType();
	}

	// 위조 방지용 상태 토큰(state token) 생성
	public String generateState() {

		return UUID.randomUUID().toString();
	}

	// 소셜로그인 인증 url 리턴
	public String getAuthorizationUrl(HttpSession session) {
		String oauthState = generateState();
		session.setAttribute("oauthState", oauthState);

		this.oauth20Service = new ServiceBuilder().apiKey(this.socialValue.getClientId())
				.apiSecret(this.socialValue.getClientSecret()).callback(this.socialValue.getCallbackUrl())
				.state(oauthState).build(this.socialValue.getApi20Instance());

		return this.oauth20Service.getAuthorizationUrl();
	}

	// 회원탈퇴시 필요한 재인증 url 리턴
	public String getReauthorizationUrl(HttpSession session) {
		String reauthState = generateState();
		session.setAttribute("reauthState", reauthState);

		this.oauth20Service = new ServiceBuilder().apiKey(this.socialValue.getClientId())
				.apiSecret(this.socialValue.getClientSecret()).callback(this.socialValue.getReauthCallbackUrl())
				.state(reauthState).build(this.socialValue.getApi20Instance());

		return this.oauth20Service.getAuthorizationUrl() + "&auth_type=reauthenticate";
	}

	// accessToken 가져오기
	public OAuth2AccessToken getAccessToken(String code, String state, HttpSession session) throws Exception {
		// 넘어온 state와 session에 저장한 oauthState가 일치하면
		if (state.equals(session.getAttribute("oauthState"))) {
			System.out.println("state, session의 oauthState 일치!");
			System.out.println("accessToken 전달!");
			OAuth2AccessToken accessToken = this.oauth20Service.getAccessToken(code);

			return accessToken;
		}

		System.out.println("ERROR!:state, session의 oauthState 불일치!");

		return null;
	}

	// 재인증 후 얻은 accessToken과 DB에 저장된 accessToken이 일치하는지 확인하는 작업
	public OAuth2AccessToken compareAccessToken(HttpSession session, String code, String state) throws IOException {
		System.out.println("================================================================");
		System.out.println("콜백 후 얻은 AccesstToken과 DB에 저장된 accessToken을 비교합니다.");
		SocialDetailVO socialDetail = new SocialDetailVO();

		if (state.equals(session.getAttribute("reauthState"))) {
			System.out.println("state, reauthState 일치!");
			String newAccessToken = this.oauth20Service.getAccessToken(code).getAccessToken(); // 인증 후 콜백 code로 얻은
																								// accessToken
			/* sdService.findBySocialDetail((long) session.getAttribute("customerCode")); */
		}

		System.out.println("state, reauthState 불일치!");

		return null;
	}

	// 프로필 정보 가져오기
	public CustomerVO getProfile(OAuth2AccessToken accessToken) throws Exception {
		Response response = null;
		OAuthRequest request = new OAuthRequest(Verb.POST, this.profileUrl, this.oauth20Service);

		this.oauth20Service.signRequest(accessToken, request);
		response = request.send();

		System.out.println("프로필 response 완료!");
		return profileParsing(response.getBody());
	}

	// profile 정보를 JSON 데이터로 변환해 필요한 데이터를 CustomerVO에 저장.
	private CustomerVO profileParsing(String profileBody) throws Exception {
		CustomerVO customer = new CustomerVO();
		JSONParser jsonParse = new JSONParser();

		// JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
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

	public SocialDetailVO getSocialDetail(long customerCode, OAuth2AccessToken accessToken) {
		System.out.println("\n여기는 SocialLogin의 insertTokenData");
		System.out.println(accessToken);

		SocialDetailVO newTokenData = new SocialDetailVO();

		newTokenData.setCustomerCode(customerCode);
		newTokenData.setAccessToken(accessToken.getAccessToken());
		newTokenData.setRefreshToken(accessToken.getRefreshToken());

		return newTokenData;
	}
}
