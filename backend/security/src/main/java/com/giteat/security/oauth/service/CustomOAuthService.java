package com.giteat.security.oauth.service;

import com.giteat.security.oauth.dto.OAuthToken;
import com.giteat.security.oauth.dto.User;
import com.giteat.security.oauth.entity.UserEntity;
import com.giteat.security.oauth.repository.OAuthTokenRepository;
import com.giteat.security.oauth.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/*
* OAuth2 인증 처리하는 서비스 클래스
*
* */

@Service
@Transactional
public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuthTokenRepository oauthTokenRepository;

    public CustomOAuthService(UserRepository userRepository, OAuthTokenRepository oauthTokenRepository) {
        this.userRepository = userRepository;
        this.oauthTokenRepository = oauthTokenRepository;
    }

    /*
    * OAuth 로그인 성공 시 호출되는 메인 메서드
    * gitlab에서 사용자 정보를 가져와서 우리 서비스에 맞게 처리한다.
    * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. security의 기본 OAuth2UserService를 사용해 기본 사용자 정보 가져온다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegete = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegete.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 2. gitlab에서 받은 정보로 user dto 설정
        User user = new User();
        user.setEmail(attributes.get("email").toString());
        user.setName(attributes.get("name").toString());
        user.setAvatarUrl(attributes.get("avatar_url").toString());

        // 3. 사용자 정보 저장 및 업데이트
        User savedUser = saveOrUpdateUser(user);

        // 4. Oauth 토큰 정보 저장
        saveOAuthToken(userRequest, savedUser, attributes);

        // 5. security가 인증에 사용할 OAuth2User 객체 반환
        return new DefaultOAuth2User(
                //기본 권한 부여
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                // gitlab에서 받은 원본 정보
                attributes,
                //사용자를 구분하는 키 값
                "id"
        );
    }
   /*
   * OAuth 토큰 정보를 저장하는 메서드
   * gitlab에서 받은 액세스 토큰과 관련 정보를 저장한다.
   * */
    private void saveOAuthToken(OAuth2UserRequest userRequest, User user, Map<String, Object> attributes) {
        OAuthToken token = new OAuthToken();

        // 1. 토큰 기본 정보 설정
        token.setUserId(user.getUserId()); // 어떤 사용자의 토큰인지
        token.setProviderType(userRequest.getClientRegistration().getRegistrationId()); // 토큰 제공자
        token.setAccessToken(userRequest.getAccessToken().getTokenValue()); // 실제 액세스 토큰
        token.setTokenType(userRequest.getAccessToken().getTokenType().getValue()); // 토큰 타입
        token.setExpiresIn((int) userRequest.getAccessToken().getExpiresAt().getEpochSecond()); // 토큰 만료 시간
        token.setScope(String.join(" ", userRequest.getAccessToken().getScopes()));
        token.setCreatedAt(LocalDateTime.now());

        // 2. 사용자 정보도 토큰과 함께 저장
        token.setEmail(user.getEmail());
        token.setName(user.getName());
        token.setAvatarUrl(user.getAvatarUrl());

        // 3. 토큰 정보를 데이터베이스에 저장
        oauthTokenRepository.save(token);
    }

    /*
    * 사용자 정보를 저장하거나 업데이트 하는 메서드
    * 이미 가입한 사용자면 정보 업데이트, 새로운 사용자면 새로 등록
    * */
    private User saveOrUpdateUser(User user) {
        // 1. 기존 사용자인지 이메일로 확인
        UserEntity existingUser = userRepository.findByEmail(user.getEmail())
                .orElse(null);

        // 2-1. 기존 사용자라면 정보 업데이트
        if(existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setAvatarUrl(user.getAvatarUrl());
            UserEntity savedEntity = userRepository.save(existingUser);

            // Entity를 DTO로 변환하여 반환
            return convertToDTO(savedEntity);
        }
        // 2-2. 새로운 사용자라면 새로 저장
        // DTO를 Entity로 변환
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setAvatarUrl(user.getAvatarUrl());

        UserEntity savedEntity = userRepository.save(userEntity);

        // Entity를 DTO로 변환하여 반환
        return convertToDTO(savedEntity);
    }

    // Entity를 DTO로 변환하는 헬퍼 메서드
    private User convertToDTO(UserEntity savedEntity) {
        User userDto = new User();
        userDto.setUserId(savedEntity.getUserId());
        userDto.setEmail(savedEntity.getEmail());
        userDto.setName(savedEntity.getName());
        userDto.setAvatarUrl(savedEntity.getAvatarUrl());
        userDto.setMmWebhook(savedEntity.getMmWebhook());
        return userDto;
    }
}
