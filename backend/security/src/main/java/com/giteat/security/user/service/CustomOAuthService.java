package com.giteat.security.user.service;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/*
* OAuth2 인증 처리하는 서비스 클래스
* */

@Service
public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthApi oauthApi;

    public CustomOAuthService(OAuthApi oauthApi) {
        this.oauthApi = oauthApi;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }

    public OAuthTokenDto gitlabLogin(String code){
        // CSRF 공격 방지를 위한 상태 토큰 생성
//        String state = UUID.randomUUID().toString();
        try {
            Map<String, String> token =  oauthApi.getAccessToken(code);

            OAuthTokenDto dto = new OAuthTokenDto();
            dto.setAccessToken(token.get("access_token"));
            dto.setTokenType(token.get("token_type"));
            dto.setRefreshToken(token.get("refresh_token"));
            dto.setExpiresIn(Integer.valueOf(token.get("expires_in")));
            dto.setScope(token.get("scope"));
            dto.setCreatedAt(LocalDateTime.now());

            Map<String, String> userInfo = oauthApi.getUserInfo(dto.getAccessToken());

            dto.setId(Integer.valueOf(userInfo.get("id")));
            dto.setUserName(userInfo.get("username"));
            dto.setEmail(userInfo.get("email"));
            dto.setName(userInfo.get("name"));
            dto.setAvatarUrl(userInfo.get("avatar_url"));

            System.out.println("service : "+ dto);
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

}


