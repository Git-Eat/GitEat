package com.giteat.security.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.security.oauth.dto.OAuthToken;
import com.giteat.security.oauth.repository.OAuthTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/*
* oauth 로그인 성공 시 처리 담당하는 핸들러
* gitlab 로그인 성공 후 토큰 정보를 JSON으로 응답하고 FE로 리다이렉트
* */

@Component

public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuthTokenRepository oAuthTokenRepository;
    private final ObjectMapper objectMapper; //JSON 변환을 위한 매퍼

    public OAuthLoginSuccessHandler(OAuthTokenRepository oAuthTokenRepository, ObjectMapper objectMapper) {
        this.oAuthTokenRepository = oAuthTokenRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2AuthenticationToken으로 형변환 (OAuth2 인증 정보를 가져오기 위해)
        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
        // OAuth2User에서 실제 사용자 정보를 가져옴
        OAuth2User oauth2User = oAuth2Token.getPrincipal();

        // 토큰 정보를 응답으로 전송할 데이터 준비
        Map<String, Object> responseData = new HashMap<>();
        // getAttribute() 메서드로 GitLab이 제공하는 각 필드의 값을 가져올 수 있음
        responseData.put("access_token", oauth2User.getAttribute("access_token"));
        responseData.put("token_type", "Bearer");
        responseData.put("expires_in", oauth2User.getAttribute("expires_in"));
        responseData.put("created_at", Instant.now().getEpochSecond()); // Unix timestamp 형식

        if(oauth2User.getAttribute("refresh_token") != null) {
            responseData.put("refresh_token", oauth2User.getAttribute("refresh_token"));
        }

        // JSON 형식으로 응답 전송
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), responseData);

        // FE페이지로 리다이렉트
        response.sendRedirect("http://localhost:5174");

    }
}
