package com.giteat.security.aop;

import com.giteat.security.util.TokenContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
@Component
public class TokenCookieAspect {

    // @Pointcut 정의: 특정 패키지 또는 클래스에 있는 메서드에 AOP 적용
    @Pointcut("execution(* com.giteat.security.*.*(..))") // 실제 경로에 맞게 수정
    public void controllerMethods() {}

    // @Before을 사용하여 메서드가 실행되기 전에 처리
    @Before(value = "controllerMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        // JoinPoint를 통해 메서드 매개변수에 접근
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        HttpServletResponse response = null;

        // 매개변수에서 HttpServletRequest와 HttpServletResponse를 찾기
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
            if (arg instanceof HttpServletResponse) {
                response = (HttpServletResponse) arg;
            }
        }

        if (request == null || response == null) {
            // request나 response가 없으면 처리하지 않음
            return;
        }

        // 요청 경로 확인
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/oauth/gitlab/login")) {
            // 특정 URI에서는 쿠키를 처리하지 않음
            return;
        }

        // 쿠키 생성 및 설정
        try {
            int maxAge = 10 * 365 * 24 * 60 * 60; // 10년
            String accessToken = TokenContext.getAccessToken();
            String refreshToken = TokenContext.getRefreshToken();

            System.out.println("새로 만들어서 반환 access : " + accessToken);
            System.out.println("새로 만들어서 반환 refresh : " + refreshToken);

            // refreshToken 쿠키 생성
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // 보안 연결에서만 쿠키 전송
            cookie.setPath("/"); // 쿠키 경로 설정
            cookie.setMaxAge(maxAge); // 쿠키 유효 기간 설정 (10년)

            // 쿠키를 응답에 추가
            response.addCookie(cookie);

            // Authorization 헤더 설정
            response.setHeader("Authorization", "Bearer " + accessToken);

            // 토큰 제거
            TokenContext.removeAccessToken();
            TokenContext.removeRefreshToken();

            System.out.println("response HEADER : " + response.getHeader("Authorization"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
