package com.giteat.security.interceptor;

import com.giteat.security.interceptor.service.OauthInterceptorService;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.dto.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class OauthInterceptor implements HandlerInterceptor {


    @Lazy
    private final OauthInterceptorService oauthInterceptorService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 쿠키에서 accessToken으로 사용자 정보를 가져온다.
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return false;
        }
         accessToken = accessToken.split(" ")[1];

        String refreshToken = oauthInterceptorService.getRefreshTokenFromCookie(request);
        User userDto = null;
        OAuthTokenDto oAuthTokenDto = null;
        if (accessToken == null || refreshToken == null) {
            return false;
        }
        String authenticationId = oauthInterceptorService.getUserIdFromUserInfo(accessToken); // 인증받은 사용자 id

        if (authenticationId == null) {        //null일 경우 accessToken이 만료되었음으로 refresh 토큰을 검사한다.
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);
            if (oAuthTokenDto == null) {          //null 일 경우 refreshToken이 만료된 값임으로 실패
                return false;
            }
            authenticationId = oauthInterceptorService.getUserIdFromUserInfo(oAuthTokenDto.getAccessToken());
        } else {
            oAuthTokenDto = oauthInterceptorService.getNewToken(refreshToken);

        }
        oauthInterceptorService.saveNewToken(authenticationId, oAuthTokenDto);


        //발급받은 access토큰을 thread에 저장하는 로직 필요
        //dto를 저장한다.


        return true;
    }
}
