package com.giteat.security.user.api;

import com.giteat.security.user.dto.OAuthToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OAuthApi {

    private final RestTemplate restTemplate;

    public static final String CLIENT_ID = "2adedad9065950161b2ff1671f8c6e13b1cc65354dc43d1ef36483416391424b";
    public static final String CLIENT_SECRET = " ";
    public static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/gitlab";
    public static final String TOKEN_URI = "https://lab.ssafy.com/oauth/token";
    public static final String USER_INFO_URI = "https://lab.ssafy.com/api/v4/user";

    public OAuthApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    * 프론트엔드에서 받은 Authorization Code를 사용하여
    * GitLab에 Access Token을 요청
    */
    public OAuthToken getAccessToken(String code) {
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        // OAuth 토큰 요청 시 (form-urlencoded 사용)
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Oauth access 토큰 요청할 때 서버가 oauth 에게 전달해주는 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", REDIRECT_URI);

        // 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // POST 요청으로 OAuthToken 응답 받기
        ResponseEntity<OAuthToken> response = restTemplate.postForEntity(
                TOKEN_URI,
                request,
                OAuthToken.class    // DTO 사용
        );

        return response.getBody();  // GitLab이 보낸 응답 본문을 OAuthToken 객체로 변환해서 반환
    }

    // 토큰 갱신
    public OAuthToken refreshAccessToken(String refreshToken) {
        // 1. HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        // OAuth 토큰 요청 시에는 application/x-www-form-urlencoded 형식 사용
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("refresh_token", refreshToken);
        // grant_type을 'refresh_token'으로 지정하여 토큰 갱신임을 명시
        params.add("grant_type", "refresh_token");
        params.add("redirect_uri", REDIRECT_URI);

        // 3. HTTP 요청 객체 생성
        // HttpEntity는 헤더와 바디를 포함한 HTTP 요청의 전체를 나타냄
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 4. POST 요청 실행
        // RestTemplate을 사용하여 GitLab OAuth 서버에 토큰 갱신 요청
        ResponseEntity<OAuthToken> response = restTemplate.postForEntity(
                TOKEN_URI,
                request,
                OAuthToken.class
        );

        // 5. 응답에서 토큰 정보 추출하여 반환
        // response.getBody()는 GitLab이 보낸 응답을 OAuthToken 객체로 변환한 것
        return response.getBody();
    }


    /*
    * gitlab api에서 사용자 정보를 가져오는 메서드
    * @param accessToken gitlab에서 받은 인증 토큰
    * @return 사용자정보가 담긴 map
    * */
    public Map<String, Object> getUserInfo(String accessToken) {
        // 1. API 호출을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        // "Bearer " + accessToken 와 동일
        headers.setBearerAuth(accessToken);

        // 2. HTTP 요청 객체 생성 (헤더만 포함)
        HttpEntity<?> request = new HttpEntity<>(headers);

        // 3. GitLab API 호출
        // - USER_INFO_URI: "https://lab.ssafy.com/api/v4/user"
        // - GET: HTTP 메소드
        // - request: 위에서 만든 요청 객체
        // - ParameterizedTypeReference: JSON 응답을 Map으로 변환하기 위한 타입 정보
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                USER_INFO_URI,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
                );

        // 4. API 응답에서 사용자 정보만 추출해서 반환
        // 반환되는 Map에는 이런 정보들이 포함됨:
        // - "username": "사용자아이디"
        // - "email": "이메일주소"
        // - "name": "사용자이름"
        // - "avatar_url": "프로필이미지URL"
        return response.getBody();
    }
}
