package org.zerock.oauth;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zerock.domain.CustomerVO;
import org.zerock.domain.SocialDetailVO;
import org.zerock.service.CustomerServiceImpl;
import org.zerock.service.SocialDetailServiceImpl;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class SocialLogin {
	@Resource
	SocialDetailServiceImpl sdService;
	
	@Resource
	CustomerServiceImpl customerService;
	
	private OAuth20Service oauth20Service;
	private String profileUrl;
	private String socialType;
	private SocialValue socialValue;

	public SocialLogin(SocialValue socialValue) {
		this.socialValue = socialValue; 
		this.profileUrl = socialValue.getProfileUrl();
		this.socialType = socialValue.getSocialType();
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

	// 재인증 후 얻은 accessToken을 가지고 얻은 프로필이 현재 로그인돼 있는 사용자 정보와 일치하는지 확인.
	public boolean compareAccessToken(HttpSession session, String code, String state) throws Exception {
		System.out.println("\n================================================================");
		System.out.println("콜백 후 얻은 AccesstToken을 이용해 얻은 프로필 정보가 사용자 정보와 일치하는지 확인합니다.");
		
		boolean iscomplete = false;	//회원 탈퇴 과정이 성공했는지, 실패했는지 결과를 담기 위한 boolean형 변수.
		
		if (state.equals(session.getAttribute("reauthState"))) {	//재인증 url을 통해 얻은 state와 session에 담긴 reauthState가 일치한다면
			System.out.println("state, reauthState 일치!");
			long customerCode = (long) session.getAttribute("customerCode");	//현재 로그인돼 있는 사용자의 고객코드
			OAuth2AccessToken newAccessToken = this.oauth20Service.getAccessToken(code); // 인증 후 콜백 code로 얻은 accessToken
			CustomerVO newProfile = getProfile(newAccessToken);	//새 토큰으로 얻은 프로필 정보
			String socialId = customerService.getSocialId(customerCode);	//현재 사용자의 socialId
			
			//현재 사용자의 socialId와 newProfile의 socialId를 비교
			if (socialId.equals(newProfile.getSocialId())) {	//socialId가 일치한다면
				System.out.println("새 토큰으로 얻은 socialId와 현재 사용자의 socialId가 일치합니다.");
				//delete token을 위한 url로 이동하기 위해 deleteToken 메소드 실행.
				System.out.println("token을 삭제하는 deleteToken 메소드를 실행합니다.");
				iscomplete = deleteToken(newAccessToken);	//Token 만료를 성공하면 iscomplete 변수에 true가 담기고, 실패하면 false가 담김.
				
				return iscomplete;	
			}
			else {	//socialId가 일치하지 않는다면
				System.out.println("새 토큰으로 얻은 socialId와 현재 사용자의 socialId가 일치하지 않습니다.");
				
				return iscomplete;	//false
			}
		}
		System.out.println("state, reauthState 불일치!");	//재인증 url callback을 통해 얻은 state와 reauthState가 일치하지 않는다면

		return iscomplete;	//false
	}

	// 프로필 정보 가져오기
	public CustomerVO getProfile(OAuth2AccessToken accessToken) throws Exception {
		Response response = null;
		OAuthRequest request = new OAuthRequest(Verb.POST, this.profileUrl, this.oauth20Service);

		this.oauth20Service.signRequest(accessToken, request);
		response = request.send();
		System.out.println("프로필 response 완료!");
		
		//body에 담긴 프로필 정보를 json 형태로 변환해 customerVO에 담기 위한 메소드인 profileParsing 실행.
		return profileParsing(response.getBody());
	}

	// profile 정보를 JSON 데이터로 변환해 필요한 데이터를 CustomerVO에 저장.
	private CustomerVO profileParsing(String profileBody) throws Exception {
		System.out.println("여기는 SocialLogin 클래스의 profileParsing 메소드");
		CustomerVO customer = new CustomerVO();
		JSONParser jsonParse = new JSONParser();

		// JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
		JSONObject jsonObj = (JSONObject) jsonParse.parse(profileBody);
		String resultCode = (String) jsonObj.get("resultcode");	//resultCode
		System.out.println("resultCode : " + resultCode);
		
		//소셜로그인에 카카오가 추가되면 네이버와 카카오의 프로필 정보 형태가 다르기 때문에 if문 작성.
		if (socialType.equals("naver")) {
			if(resultCode.equals("00")) {	//naver에서는 프로필 정보 호출이 성공하면 결과값이 00.
				System.out.println("프로필 정보를 성공적으로 호출했습니다.");
				jsonObj = (JSONObject) jsonObj.get("response");
				
				//customerVO에 해당 프로필 정보 담기.
				customer.setSocialType("naver");
				customer.setSocialId((String) jsonObj.get("id"));
				customer.setCustomerEmail((String) jsonObj.get("email"));
				customer.setCustomerPhone((String) jsonObj.get("mobile"));
				customer.setCustomerName((String) jsonObj.get("name"));
			}
			
			return customer;
		}
		else {	//결과코드가 실패코드면
			System.out.println("프로필 정보 호출을 실패했습니다.");
			
			return null;
		}
	}

	//Token 관련해서 업데이트가 일어났을 때 사용하는 메소드
	public SocialDetailVO getSocialDetail(long customerCode, OAuth2AccessToken accessToken) {
		System.out.println("\n여기는 SocialLogin의 getSocialDetail");

		SocialDetailVO newTokenData = new SocialDetailVO();

		newTokenData.setCustomerCode(customerCode);
		newTokenData.setAccessToken(accessToken.getAccessToken());
		newTokenData.setRefreshToken(accessToken.getRefreshToken());

		return newTokenData;
	}
	
	//회원탈퇴 api url로 이동 - Token delete(토큰 만료) 처리
	public boolean deleteToken(OAuth2AccessToken accessToken) throws Exception {
		System.out.println("socialLogin 클래스의 test 메소드");
		//토큰 delete를 위한 api url 호출
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, String>> deleteTokenRequest = new HttpEntity<>(headers);
		
		//호출된 api url 이동해 얻은 response 처리
		RestTemplate rt = new RestTemplate();
		String deleteApiUrl = "";	//해당 social 토큰 만료 api url
		//소셜로그인의 유형마다 토큰 만료 api url이 다르기 때문에 if문 작성.
		if (this.socialType.equals("naver")) {
			deleteApiUrl = this.socialValue.getDeleteTokenUrl() + "&client_id=" + this.socialValue.getClientId() + 
					"&client_secret=" + this.socialValue.getClientSecret() + "&access_token=" + accessToken.getAccessToken() + "&service_provider=NAVER";
		}
		ResponseEntity<String> response = rt.exchange(
				  deleteApiUrl,
			      HttpMethod.POST,
			      deleteTokenRequest,
			      String.class
		);
		
		//얻어낸 response의 body부분을 json 형태로 변환.
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParse.parse(response.getBody());
		String result = (String) jsonObj.get("result");	//토큰 만료 결과를 얻기 위한 result 변수.
		if (result.equals("success")) {	//토큰 만료를 성공했으면
			System.out.println("토큰 삭제를 완료했습니다.");
			
			return true;
		}
		else {	//토큰 만료를 실패했다면
			System.out.println("토큰 삭제를 실패했습니다.");
			
			return false;
		}
	}
}
