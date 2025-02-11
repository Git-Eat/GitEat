package com.giteat.security.user.service;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;

import com.giteat.security.user.dto.User;
import com.giteat.security.util.TokenContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public OAuthTokenDto gitlabLogin(String code , HttpServletResponse response ){
        // CSRF 공격 방지를 위한 상태 토큰 생성
        // String state = UUID.randomUUID().toString();
        try {
            Map<String, String> token =  oauthApi.getAccessToken(code);
            System.out.println("받아온 토큰 정보: " + token);

            // 토큰이 비어있는지 확인
            if (token.isEmpty()) {
                System.out.println("토큰이 비어있습니다");
                return null;
            }

            OAuthTokenDto dto = new OAuthTokenDto();
            dto.setAccessToken(token.get("access_token"));
            System.out.println("access_token 설정: " + dto.getAccessToken());

            dto.setTokenType(token.get("token_type"));
            dto.setRefreshToken(token.get("refresh_token"));
            dto.setExpiresIn(Integer.valueOf(token.get("expires_in")));
            dto.setScope(token.get("scope"));
//            dto.setCreatedAt(LocalDateTime.now());

            Map<String, String> userInfo = oauthApi.getUserInfo(dto.getAccessToken());
            System.out.println("받아온 사용자 정보: " + userInfo);
            if (userInfo.isEmpty()) {
                System.out.println("사용자 정보가 비어있습니다");
                return null;
            }

            dto.setUserId(Integer.valueOf(userInfo.get("id")));
            dto.setUserName(userInfo.get("username"));
            dto.setEmail(userInfo.get("email"));
            dto.setName(userInfo.get("name"));
            dto.setAvatarUrl(userInfo.get("avatar_url"));

            System.out.println("service: "+ dto);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("service gitlabLogin 에러: " + e.getMessage());
            return null;
        }
    }
    public Map<String , String> getMyInfo(){
        String accessToken = TokenContext.getAccessToken();
        Map<String , String> userInfo = oauthApi.getUserInfo(accessToken);
        return userInfo;
    }

    public void createCookieAndToken(String accessToken , String refreshToken, HttpServletResponse response){
        response.setHeader("Authorization", "Bearer " + accessToken);

        // 쿠키 설정 (accessToken을 쿠키에 추가)
        int maxAge = 10 * 365 * 24 * 60 * 60;
        Cookie accessTokenCookie = new Cookie("refreshToken", refreshToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(maxAge);

        // 쿠키를 응답에 추가
        response.addCookie(accessTokenCookie);
    }
}


