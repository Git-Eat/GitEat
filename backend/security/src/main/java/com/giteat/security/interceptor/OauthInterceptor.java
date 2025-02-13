package com.giteat.security.interceptor;

import com.giteat.security.interceptor.service.OauthInterceptorService;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.dto.User;
import com.giteat.security.util.TokenContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class OauthInterceptor implements HandlerInterceptor {

    private final OauthInterceptorService oauthInterceptorService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("REQUEST URI : " + requestURI);

        if (requestURI.startsWith("/api/oauth/gitlab/login") || requestURI.startsWith("/api/oauth/gitlab/refresh")) {
            return true;
        }

        // 1. 쿠키에서 accessToken으로 사용자 정보를 가져온다.
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            responseSetting(response);
            log.error("accessToken is empty or null");
            return false;
        }
        accessToken = accessToken.split(" ")[1];
        String authenticationId = oauthInterceptorService.getUserIdFromUserInfo(accessToken);
        if (authenticationId == null) {
            responseSetting(response);
            return false;
        }
        TokenContext.setAccessToken(accessToken);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        TokenContext.removeAccessToken();
        TokenContext.removeRefreshToken();
    }

    public void responseSetting(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Missing or invalid token\"}");
    }

}
