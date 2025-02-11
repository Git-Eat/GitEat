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
        if(requestURI.startsWith("/api/oauth/gitlab/login")){
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

        System.out.println("받은 accessToken : "  + accessToken);
        System.out.println("받은 refreshToken : " + refreshToken);
        User userDto = null;
        OAuthTokenDto oAuthTokenDto = null;
        if (refreshToken == null) {
            responseSetting(response);
            log.error("refreshToken is Empty");
            return false;
        }
        String authenticationId = oauthInterceptorService.getUserIdFromUserInfo(accessToken); // 인증받은 사용자 id
        System.out.println("사용자 id 값 : " + authenticationId);
        if (authenticationId == null) {        //null일 경우 accessToken이 만료되었음으로 refresh 토큰을 검사한다.
            log.info("if문 안에서 호출");
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);
            System.out.println("if문 dto :" + oAuthTokenDto);
            if (oAuthTokenDto == null) {          //null 일 경우 refreshToken이 만료된 값임으로 실패
                responseSetting(response);
                log.error("refreshToken is invalid");
                return false;
            }
            authenticationId = oauthInterceptorService.getUserIdFromUserInfo(oAuthTokenDto.getAccessToken());
        } else {
            log.info("else문 안에서 호출");
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);
            System.out.println("else dto : " + oAuthTokenDto);

        }
        oauthInterceptorService.saveNewToken(authenticationId, oAuthTokenDto);
        String newAccessToken = oAuthTokenDto.getAccessToken();
        String newRefreshToken = oAuthTokenDto.getRefreshToken();
        TokenContext.setAccessToken(newAccessToken);
        TokenContext.setRefreshToken(newRefreshToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("처음 진입 response : "   + response.toString());
        String requestURI = request.getRequestURI();
        System.out.println("종료 경로 : " + requestURI);
        if(requestURI.startsWith("/api/oauth/gitlab/login")){
            return;
        }
        System.out.println("return 할때 cookie 생성");

        int maxAge = 10 * 365 * 24 * 60 * 60;
        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();
        System.out.println("새로 만들어서 반환 access : " + accessToken);
        System.out.println("새로 만들어서 반환 refresh : " + refreshToken);
        // cookie 설정

        try{
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
            System.out.println("resonse HEADER :  " + response.getHeader("Authorization"));
            System.out.println("모든 postHandle 실행완료");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void responseSetting(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Missing or invalid token\"}");
    }

}
