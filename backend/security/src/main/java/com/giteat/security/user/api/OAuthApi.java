package com.giteat.security.user.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class OAuthApi {

    private final RestTemplate restTemplate;

    private static final String CLIENT_ID = "8a9363db17d9a7aae6c03c37f43eec0f942e30bb88541ff8bb6aaf46b17aa6b2";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://127.0.0.1:5173/loading";
    private static final String TOKEN_URI = "https://lab.ssafy.com/oauth/token";
    private static final String USER_INFO_URI = "https://lab.ssafy.com/api/v4/user";

    public OAuthApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    * 프론트엔드에서 받은 Authorization Code를 사용하여
    * GitLab에 Access Token을 요청
    */
    public Map<String, String> getAccessToken(String code) {
        // HTTP 요청 헤더 설정

        try {
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

            ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URI, request, String.class);

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());

            Map<String, String> map = new HashMap<>();
            map.put("access_token", jsonNode.get("access_token").asText());
            map.put("token_type", jsonNode.get("token_type").asText());
            map.put("refresh_token", jsonNode.get("refresh_token").asText());
            map.put("expires_in", jsonNode.get("expires_in").asText());
            map.put("scope", jsonNode.get("scope").asText());
            map.put("created_at", jsonNode.get("created_at").asText());

            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /*
    * gitlab api에서 사용자 정보를 가져오는 메서드
    * @param accessToken gitlab에서 받은 인증 토큰
    * @return 사용자정보가 담긴 map
    * */

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
                    USER_INFO_URI,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());

            // 4. API 응답에서 사용자 정보만 추출해서 반환
            // 반환되는 Map에는 이런 정보들이 포함됨:
            // - "username": "사용자아이디"
            // - "email": "이메일주소"
            // - "name": "사용자이름"
            // - "avatar_url": "프로필이미지URL"
            Map<String, String> map = new HashMap<>();
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
