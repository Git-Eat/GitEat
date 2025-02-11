package com.giteat.security.aop;

import com.giteat.security.util.TokenContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Pointcut("execution(* com.giteat.security.*.*(..))")  // 컨트롤러의 모든 메서드
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object aroundControllerMethods(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aop 실행!!!!");


        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];  // 요청 인자
        HttpServletResponse response = (HttpServletResponse) joinPoint.getArgs()[1]; // 응답 인자

        // 요청 경로 확인
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/oauth/gitlab/login")) {
            // 특정 URI에서는 쿠키 처리하지 않음
            return joinPoint.proceed();
        }

        // AOP에서 실행 전후 로직 추가 가능
        try {
            // 컨트롤러 메서드 실행 전
            Object result = joinPoint.proceed();

            // 실행 후 쿠키 추가
            System.out.println("return 할때 cookie 생성");

            int maxAge = 10 * 365 * 24 * 60 * 60; // 10년
            String accessToken = TokenContext.getAccessToken();
            String refreshToken = TokenContext.getRefreshToken();

            System.out.println("새로 만들어서 반환 access : " + accessToken);
            System.out.println("새로 만들어서 반환 refresh : " + refreshToken);

            // 쿠키 설정
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);

            response.addCookie(cookie);
            response.setHeader("Authorization", "Bearer " + accessToken);

            // 토큰 삭제
            TokenContext.removeAccessToken();
            TokenContext.removeRefreshToken();

            System.out.println("resonse HEADER :  " + response.getHeader("Authorization"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
