package com.giteat.security.oauth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* OAuth2 인증 처리하는 서비스 클래스
*
* */

@Service
@Transactional
public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /*
    * OAuth 로그인 성공 시 호출되는 메인 메서드
    * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. security의 기본 OAuth2UserService를 사용해 기본 사용자 정보 가져온다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegete = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegete.loadUser(userRequest);

        // 2. 현재 진행중인 로그인 서비스가 어떤 것인지 구분하기.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. OAuth 로그인 시 키가 되는 필드값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        return null;
    }
}
