package com.giteat.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.user.model.repository.OAuthRepository;
import com.giteat.user.model.dto.OAuthTokenDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GitLabApi {

    private final OAuthRepository oAuthDao;
    private final RestTemplate restTemplate;

    public GitLabApi(OAuthRepository oAuthDao, RestTemplate restTemplate) {
        this.oAuthDao = oAuthDao;
        this.restTemplate = restTemplate;
    }

    private static final String CLIENT_ID = "8a9363db17d9a7aae6c03c37f43eec0f942e30bb88541ff8bb6aaf46b17aa6b2";
    private static final String CLIENT_SECRET = "gloas-c535103e2d14667af66a61a71267dcf1e49e878c84e560a91aee907bc4a10363";
    private static final String REDIRECT_URI = "http://127.0.0.1:5173/loading";
    private static final String TOKEN_URI = "https://lab.ssafy.com/oauth/token";
    private static final String USER_INFO_URI = "https://lab.ssafy.com/api/v4/user";

    public Map<String, String> refreshAccessToken(OAuthTokenDto tokenRequest) {

        // parameters = 'client_id=APP_ID&client_secret=APP_SECRET&refresh_token=REFRESH_TOKEN&grant_type=refresh_token&redirect_uri=REDIRECT_URI'
        try {
            System.out.println("refreshAccessToken: " + tokenRequest);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", CLIENT_ID);
            params.add("client_secret", CLIENT_SECRET);
            params.add("refresh_token", tokenRequest.getRefreshToken());
            params.add("grant_type", "refresh_token");
            params.add("redirect_uri", REDIRECT_URI);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // 토큰 갱신
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    TOKEN_URI,
                    HttpMethod.POST,
                    request,
                    String.class);
            System.out.println("api 응답 내용: " + response.getBody());

            // JSON 파싱 및 토큰 갱신에 대한 응답
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());

            // 새로운 토큰 반환
            Map<String, String> map = new HashMap<>();
            map.put("access_token", jsonNode.get("access_token").asText());
            map.put("token_type", jsonNode.get("token_type").asText());
            map.put("expires_in", jsonNode.get("expires_in").asText());
            map.put("refresh_token", jsonNode.get("refresh_token").asText());
            map.put("created_at", jsonNode.get("created_at").asText());

            System.out.println("토큰갱신성공: " + map);
            return map;

        } catch (HttpClientErrorException e) {
            System.out.println("RefreshAccessToken 재발급 실패: " + tokenRequest);
            return null;

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

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

            System.out.println("getUserInfo map: " + map);
            return map;

        } catch (Exception e) {
            System.out.println("api호출 실패: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
