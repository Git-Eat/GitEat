package com.giteat.security.user.service;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * OAuth2 인증 처리를 담당하는 서비스 클래스
 * GitLab OAuth 로그인 및 사용자 정보 매핑 기능 제공
 */
@Service
public class CustomOAuthService  {

    private final OAuthApi oauthApi;
    public CustomOAuthService(OAuthApi oauthApi) {
        this.oauthApi = oauthApi;
    }

    /**
     * GitLab OAuth 로그인 처리
     * Authorization Code를 사용하여 액세스 토큰을 발급받고,
     * 발급받은 토큰으로 사용자 정보를 조회하여 DTO로 변환
     *
     * @param code GitLab에서 받은 Authorization Code
     * @return OAuth 토큰 및 사용자 정보가 매핑된 DTO
     *         실패 시 null 반환
     */
    public OAuthTokenDto gitlabLogin(String code){
        // CSRF 공격 방지를 위한 상태 토큰 생성
        // String state = UUID.randomUUID().toString();
        try {
            Map<String, String> token =  oauthApi.getAccessToken(code);
            OAuthTokenDto dto = new OAuthTokenDto();
            dto.setAccessToken(token.get("access_token"));
            dto.setTokenType(token.get("token_type"));
            dto.setRefreshToken(token.get("refresh_token"));
            dto.setExpiresIn(Integer.valueOf(token.get("expires_in")));
            dto.setScope(token.get("scope"));
//            dto.setCreatedAt(LocalDateTime.now());

            Map<String, String> userInfo = oauthApi.getUserInfo(dto.getAccessToken());
            dto.setId(Integer.valueOf(userInfo.get("id")));
            dto.setUserName(userInfo.get("username"));
            dto.setEmail(userInfo.get("email"));
            dto.setName(userInfo.get("name"));
            dto.setAvatarUrl(userInfo.get("avatar_url"));
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

}


