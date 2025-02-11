package com.giteat.security.interceptor.service;

import com.giteat.security.user.mapper.OauthMapper;
import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.repository.OauthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class OauthInterceptorService {


    private final OAuthApi oAuthApi;
    private final OauthMapper oauthMapper;
//    private final OauthRepository oauthRepository;





    /**
     * accessToeken을 기반으로 사용자의 id를 반환하는 함수
     * @param accessToekn
     * @return
     */
    public String getUserIdFromUserInfo(String accessToekn){
        Map<String , String> userInfo = oAuthApi.getUserInfo(accessToekn);
        if(userInfo == null){
            return null;
        }
        return userInfo.get("id");
    }

    /**
     * cookie에서 refresh 토큰을 꺼내는 함수
     * @param request
     * @return
     */
    public String getRefreshTokenFromCookie(HttpServletRequest request){
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if("refreshToken".equals(cookie.getName())){
                refreshToken = cookie.getValue();
            }
        }
        return refreshToken;
    }

    /**
     * 새로운 토큰을 발급받고 DTO에 저장하는 함수
     * @param refreshToken
     * @return
     */
    public OAuthTokenDto getNewToken(String refreshToken) {
        Map<String , String> newTokenMap = oAuthApi.getNewToken(refreshToken);
        if(newTokenMap == null){
            return null;
        }
        OAuthTokenDto oAuthTokenDto = new OAuthTokenDto();
        oAuthTokenDto.setAccessToken(newTokenMap.get("access_token"));
        oAuthTokenDto.setTokenType(newTokenMap.get("token_type"));
        oAuthTokenDto.setExpiresIn(Integer.valueOf(newTokenMap.get("expires_in")));
        oAuthTokenDto.setRefreshToken(newTokenMap.get("refresh_token"));
        oAuthTokenDto.setCreatedAt(LocalDateTime.parse(newTokenMap.get("create_at")));

        return oAuthTokenDto;
    }


    /**
     * token 값을 받아서 update하는 함수
     * @param authenticationId
     * @param oauthTokenDto
     */
    public void saveNewToken(String authenticationId , OAuthTokenDto oauthTokenDto) {
        oauthTokenDto.setUserId(Integer.valueOf(authenticationId));
        oauthMapper.updateNewToken(oauthTokenDto);

    }




}
