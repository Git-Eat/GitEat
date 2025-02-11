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
        if(requestURI.startsWith("/api/gitlab/login")){
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

        String refreshToken = oauthInterceptorService.getRefreshTokenFromCookie(request);
        User userDto = null;
        OAuthTokenDto oAuthTokenDto = null;
        if (refreshToken == null) {
            responseSetting(response);
            log.error("refreshToken is Empty");
            return false;
        }
        String authenticationId = oauthInterceptorService.getUserIdFromUserInfo(accessToken); // 인증받은 사용자 id

        if (authenticationId == null) {        //null일 경우 accessToken이 만료되었음으로 refresh 토큰을 검사한다.
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);
            if (oAuthTokenDto == null) {          //null 일 경우 refreshToken이 만료된 값임으로 실패
                responseSetting(response);
                log.error("refreshToken is invalid");
                return false;
            }
            authenticationId = oauthInterceptorService.getUserIdFromUserInfo(oAuthTokenDto.getAccessToken());
        } else {
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);

        }
        oauthInterceptorService.saveNewToken(authenticationId, oAuthTokenDto);
        String newAccessToken = oAuthTokenDto.getAccessToken();
        TokenContext.setAccessToken(newAccessToken);
        TokenContext.setRefreshToken(refreshToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/api/gitlab/login")){
            return;
        }

        int maxAge = 10 * 365 * 24 * 60 * 60;
        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();

        // cookie 설정
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);

        // accessToken을 HTTP 응답 헤더에 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        TokenContext.removeAccessToken();
        TokenContext.removeRefreshToken();
    }

    public void responseSetting(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Missing or invalid token\"}");
    }

}
