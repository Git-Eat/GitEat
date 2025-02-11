package com.giteat.security.filter;

import com.giteat.security.util.TokenContext;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.LogRecord;

public class OauthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // HttpServletRequest와 HttpServletResponse로 변환
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Controller가 처리되기 전에 할 작업 (필요시)
        // chain.doFilter(request, response) 호출 전에 작업을 할 수 있습니다.

        // 실제로 Controller로 요청을 전달
        chain.doFilter(request, response);

        // Controller가 실행된 후 응답이 전송되기 전에 작업을 할 수 있습니다.
        // 예: 응답에 쿠키 추가
        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();

        // 쿠키 설정
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 사용 시 true 설정
        cookie.setPath("/");    // 모든 경로에서 접근 가능
        cookie.setMaxAge(10 * 365 * 24 * 60 * 60);  // 10년 설정

        httpResponse.addCookie(cookie);  // 응답에 쿠키 추가

        // 액세스 토큰을 헤더에 추가
        httpResponse.setHeader("Authorization", "Bearer " + accessToken);
        System.out.println("필터에서 쿠키 설정 완료");
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
