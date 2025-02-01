package com.giteat.security.user.service;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/*
* OAuth2 인증 처리하는 서비스 클래스
*
* */

@Service
@Transactional
public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthApi oauthApi;

    public CustomOAuthService(OAuthApi oauthApi) {
        this.oauthApi = oauthApi;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }


    public Object gitlabLogin() {
        // CSRF 공격 방지를 위한 상태 토큰 생성
        String state = UUID.randomUUID().toString();

        // GitLab Oauth 인증 url 생성
        return String.format("https://lab.ssafy.com/oauth/authorize" +
                "?client_id=%s" +  // 첫 번째 %s에 OauthApi.CLIENT_ID 값이 들어감
                "&redirect_uri=%s" +  // 두 번째 %s에 OauthApi.REDIRECT_URI 값이 들어감
                "&response_type=code" +  // 고정 값
                "&state=%s" +  // 세 번째 %s에 state 변수 값이 들어감
                "&scope=%s",  // 네 번째 %s에 "read_user api" 문자열이 들어감
                oauthApi.CLIENT_ID,  // 첫 번째 %s를 대체
                oauthApi.REDIRECT_URI,  // 두 번째 %s를 대체
                state,  // 세 번째 %s를 대체
                "read_user api");  // 네 번째 %s를 대체
    }

    /*
    * 깃랩 인증 콜백 처리 (Authorization code -> 토큰)
    * - Authorization code를 사용해 Access 토큰과 Refresh 토큰을 발급받음
    * */
    public Object gitlabCallback(String code) {

        OAuthToken oauthToken = oauthApi.getAccessToken(code);

        // 사용자 정보도 함께 가져옴
        Map<String, Object> userInfo = oauthApi.getUserInfo(oauthToken.getAccessToken());
        oauthToken.setEmail(userInfo.get("email").toString());
        oauthToken.setName(userInfo.get("name").toString());
        oauthToken.setAvatarUrl(userInfo.get("avatar_url").toString());

        // 토큰 발급 시간 설정
        oauthToken.setCreatedAt(LocalDateTime.now());

        return oauthToken;
    }




//    public void gitlabLogout(String accessToken) {
//
//    }
}
