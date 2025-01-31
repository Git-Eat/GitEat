//package com.giteat.security.user.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.giteat.security.user.repository.OAuthTokenRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
///*
//* oauth 로그인 성공 시 처리 담당하는 핸들러
//* gitlab 로그인 성공 후 토큰 정보를 JSON으로 응답하고 FE로 리다이렉트
//* */
//
//@Component
//public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final OAuthTokenRepository oAuthTokenRepository;
//    private final ObjectMapper objectMapper;
//    // OAuth2AuthorizedClientService 주입 추가
//    private final OAuth2AuthorizedClientService clientService;
//
//    public OAuthLoginSuccessHandler(OAuthTokenRepository oAuthTokenRepository, ObjectMapper objectMapper, OAuth2AuthorizedClientService clientService) {
//        this.oAuthTokenRepository = oAuthTokenRepository;
//        this.objectMapper = objectMapper;
//        this.clientService = clientService;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // OAuth2AuthenticationToken으로 형변환 (OAuth2 인증 정보를 가져오기 위해)
//        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
//
//        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
//                oAuth2Token.getAuthorizedClientRegistrationId(),
//                oAuth2Token.getName()
//        );
//
////        // OAuth2User에서 실제 사용자 정보를 가져옴
////        OAuth2User oauth2User = oAuth2Token.getPrincipal();
//
//        // 토큰 정보를 응답으로 전송할 데이터 준비
//        Map<String, Object> responseData = new HashMap<>();
//        // getAttribute() 메서드로 GitLab이 제공하는 각 필드의 값을 가져올 수 있음
//        responseData.put("access_token", client.getAccessToken().getTokenValue());
//        responseData.put("token_type", "Bearer");
//        responseData.put("expires_in", client.getAccessToken().getExpiresAt().getEpochSecond());
//        responseData.put("created_at", Instant.now().getEpochSecond()); // Unix timestamp 형식
//
//        // refresh token이 있다면 추가
//        if (client.getRefreshToken() != null) {
//            responseData.put("refresh_token", client.getRefreshToken().getTokenValue());
//        }
//
//        // 토큰 정보를 쿼리 파라미터로 변환
//        String tokenInfo = objectMapper.writeValueAsString(responseData);
//        String encodedTokenInfo = URLEncoder.encode(tokenInfo, StandardCharsets.UTF_8);
//
//        // 토큰 정보를 포함하여 리다이렉트
//        response.sendRedirect("http://localhost:5173?token=" + encodedTokenInfo);
//
//        // // JSON 형식으로 응답 전송
//        // response.setContentType("application/json");
//        // response.setCharacterEncoding("UTF-8");
//        // objectMapper.writeValue(response.getWriter(), responseData);
//
//        // // FE페이지로 리다이렉트
//        // response.sendRedirect("http://localhost:5174");
//    }
//}
