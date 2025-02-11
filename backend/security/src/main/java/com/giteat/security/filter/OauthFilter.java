package com.giteat.security.filter;

import com.giteat.security.util.TokenContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class OauthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Controller로 요청을 전달하기 전에 할 작업
        String requestURI = httpRequest.getRequestURI();

        // "/api/oauth/gitlab/login" 경로를 제외한 나머지에 대해서만 필터 적용
        if (requestURI.startsWith("/api/oauth/gitlab/login")) {
            chain.doFilter(request, response); // 요청 처리 후 바로 종료
            return;
        }

        // 실제로 Controller로 요청을 전달
        chain.doFilter(request, response);

        // Controller가 실행된 후 응답이 전송되기 전에 처리할 작업
        // 토큰을 쿠키 및 헤더에 추가하는 작업
        try {
            int maxAge = 10 * 365 * 24 * 60 * 60;  // 10년 설정
            String accessToken = TokenContext.getAccessToken();
            String refreshToken = TokenContext.getRefreshToken();

            // 새로 발급된 액세스 토큰과 리프레시 토큰을 로그로 출력
            System.out.println("새로 만들어서 반환 access : " + accessToken);
            System.out.println("새로 만들어서 반환 refresh : " + refreshToken);

            // Refresh Token을 쿠키로 설정
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // HTTPS 사용 시 true 설정
            cookie.setPath("/");
            cookie.setMaxAge(maxAge); // 만료 기간 설정

            httpResponse.addCookie(cookie);  // 응답에 쿠키 추가

            // 액세스 토큰을 Authorization 헤더에 추가
            httpResponse.setHeader("Authorization", "Bearer " + accessToken);

            // TokenContext에서 토큰을 제거하여 더 이상 사용하지 않도록 처리
            TokenContext.removeAccessToken();
            TokenContext.removeRefreshToken();

            // 응답 헤더 확인
            System.out.println("resonse HEADER :  " + httpResponse.getHeader("Authorization"));
            System.out.println("모든 postHandle 실행완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 로직 (필요 시)
    }

    @Override
    public void destroy() {
        // 필터 종료 시 리소스 정리 (필요 시)
    }
}
