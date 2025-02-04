package com.giteat.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.user.model.repository.OAuthRepository;
import com.giteat.user.model.dto.OAuthTokenDto;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * GitLab API와의 통신을 담당하는 컴포넌트
 * OAuth 인증 관련 API 호출 및 토큰 갱신 기능을 제공
 */
@Component
public class GitLabApi {

    private final OAuthRepository oAuthDao;
    private final RestTemplate restTemplate;

    public GitLabApi(OAuthRepository oAuthDao, RestTemplate restTemplate) {
        this.oAuthDao = oAuthDao;
        this.restTemplate = restTemplate;
    }

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

    /**
     * GitLab OAuth 액세스 토큰 갱신
     * refresh_token을 사용하여 새로운 액세스 토큰을 발급받음
     *
     * @param tokenRequest 갱신에 필요한 토큰 정보를 담고 있는 DTO
     * @return 갱신된 토큰 정보를 담은 Map (access_token, token_type, expires_in, refresh_token, created_at)
     *         실패 시 null 반환
     * @throws RuntimeException JSON 파싱 실패 시 발생
     */
    public Map<String, String> refreshAccessToken(OAuthTokenDto tokenRequest) {

        try {
            // OAuth 토큰 갱신을 위한 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("refresh_token", tokenRequest.getRefreshToken());
            params.add("grant_type", "refresh_token");
            params.add("redirect_uri", redirectUri);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // 토큰 갱신
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUri,
                    HttpMethod.POST,
                    request,
                    String.class);

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

            return map;

        } catch (HttpClientErrorException e) {
            return null;

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
