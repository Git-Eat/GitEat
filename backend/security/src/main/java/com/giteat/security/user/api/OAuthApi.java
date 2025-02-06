package com.giteat.security.user.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * GitLab OAuth 인증을 위한 API 호출을 담당하는 컴포넌트
 * AccessToken 발급 및 사용자 정보 조회 기능 제공
 */
@Component
public class OAuthApi {

    private final RestTemplate restTemplate;

    @Value("${oauth.gitlab.client-id}")
    private String clientId;

    @Value("${oauth.gitlab.client-secret}")
    private String clientSecret;

    @Value("${oauth.gitlab.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.gitlab.token-uri}")
    private String tokenUri;

    @Value("${oauth.gitlab.user-info-uri}")
    private String userInfoUri;

    public OAuthApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Authorization Code를 사용하여 GitLab OAuth Access Token을 요청
     *
     * @param code 프론트엔드로부터 받은 인증 코드
     * @return OAuth 토큰 정보를 담은 Map (access_token, token_type, refresh_token, expires_in, scope, created_at)
     *         실패 시 빈 Map 반환
     */
    public Map<String, String> getAccessToken(String code) {
        // HTTP 요청 헤더 설정
        try {
            //HttpHeaders headers = new HttpHeaders();
            // OAuth 토큰 요청 시 (form-urlencoded 사용)
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Oauth access 토큰 요청할 때 서버가 oauth 에게 전달해주는 파라미터
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("code", code);
            params.add("grant_type", "authorization_code");
            params.add("redirect_uri", redirectUri);

            // 요청 객체 생성
            //HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);


            System.out.println("토큰 요청 전 정보:");
            System.out.println("요청 URL: " + tokenUri);
            //System.out.println("요청 헤더: " + headers);
            System.out.println("요청 바디: " + params);

            //request test
            Map<String, String> param = new HashMap<>();
            param.put("client_id", clientId);
            param.put("client_secret", clientSecret);
            param.put("code", code);
            param.put("grant_type", "authorization_code");
            param.put("redirect_uri", redirectUri);

            System.out.println("요청 바디2: " + param);

//            ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, param, String.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);

            ResponseEntity<Map> response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, Map.class);
            System.out.println("api response" +response);

            System.out.println("토큰 응답 결과:");
            System.out.println("응답 상태코드: " + response.getStatusCode());
            System.out.println("응답 바디: " + response.getBody());

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            //JsonNode jsonNode = mapper.readTree(response.getBody());

//            Map<String, String> map = new HashMap<>();
//            map.put("access_token", jsonNode.get("access_token").asText());
//            map.put("token_type", jsonNode.get("token_type").asText());
//            map.put("refresh_token", jsonNode.get("refresh_token").asText());
//            map.put("expires_in", jsonNode.get("expires_in").asText());
//            map.put("scope", jsonNode.get("scope").asText());
//            map.put("created_at", jsonNode.get("created_at").asText());

            //System.out.println("파싱된 토큰 정보: " + map);
            return param;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * GitLab API를 통해 사용자 정보를 조회
     *
     * @param accessToken GitLab에서 발급받은 OAuth 액세스 토큰
     * @return 사용자 정보를 담은 Map (id, username, email, name, avatar_url)
     *         실패 시 빈 Map 반환
     */

    public Map<String, String> getUserInfo(String accessToken) {
        try {
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

            ResponseEntity<String> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());

            // 4. API 응답에서 사용자 정보만 추출해서 반환
            // 반환되는 Map에는 이런 정보들이 포함됨:
            // - "id": "oauth 고유 아이디값"
            // - "username": "사용자아이디"
            // - "email": "이메일주소"
            // - "name": "사용자이름"
            // - "avatar_url": "프로필이미지URL"
            Map<String, String> map = new HashMap<>();
            map.put("id", jsonNode.get("id").asText());
            map.put("username", jsonNode.get("username").asText());
            map.put("email", jsonNode.get("email").asText());
            map.put("name", jsonNode.get("name").asText());
            map.put("avatar_url", jsonNode.get("avatar_url").asText());

            return map;

        } catch(Exception e) {
            return new HashMap<>();
        }



    }


}

