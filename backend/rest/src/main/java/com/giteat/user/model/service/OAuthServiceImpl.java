package com.giteat.user.model.service;

import com.giteat.common.GitLabApi;
import com.giteat.user.entity.UserEntity;
import com.giteat.user.model.repository.UserRepository;
import com.giteat.user.model.dto.OAuthTokenDto;
import com.giteat.user.entity.OAuthEntity;
import com.giteat.user.model.repository.OAuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class OAuthServiceImpl implements OAuthService {

    private final OAuthRepository oAuthRepository;
    private final UserRepository userRepository;
    private final GitLabApi api;

    public OAuthServiceImpl(OAuthRepository oAuthDao, OAuthRepository oAuthRepository, UserRepository userDao, UserRepository userRepository, GitLabApi api) {
        this.oAuthRepository = oAuthRepository;
        this.userRepository = userRepository;
        this.api = api;
    }


    public void saveToken(OAuthTokenDto oAuthTokenDto) {
        Map<String, String> userInfo = api.getUserInfo(oAuthTokenDto.getAccessToken());
        oAuthTokenDto.setUserId(userInfo.get("id"));
        oAuthTokenDto.setUserName(userInfo.get("username"));
        oAuthTokenDto.setAvatarUrl(userInfo.get("avatar_url"));
        oAuthTokenDto.setName(userInfo.get("name"));

        System.out.println("service : " + oAuthTokenDto);
        UserEntity userEntity;

        // 이메일로 기존 사용자 검색
        Optional<UserEntity> existUser = userRepository.findByEmail(oAuthTokenDto.getEmail());

        // 기존 사용자면 그대로 사용
        if (existUser.isPresent()) {
            userEntity = existUser.get();
        } else {
            // 신규 사용자면 새로 저장
            userEntity = new UserEntity();
            userEntity.setEmail(oAuthTokenDto.getEmail());
            userEntity.setName(oAuthTokenDto.getName());
            userEntity.setAvatarUrl(oAuthTokenDto.getAvatarUrl());
            userEntity = userRepository.save(userEntity);
        }


        // 이메일로 기존 사용자 확인
        Optional<OAuthEntity> existOAuth = oAuthRepository.findByEmail(oAuthTokenDto.getEmail());
        if (existOAuth.isPresent()) {
            OAuthEntity oAuthEntity = existOAuth.get();
            oAuthEntity.setAccessToken(oAuthTokenDto.getAccessToken());
            oAuthEntity.setRefreshToken(oAuthTokenDto.getRefreshToken());
            oAuthEntity.setExpiresIn(oAuthTokenDto.getExpiresIn());
            oAuthEntity.setTokenType(oAuthTokenDto.getTokenType());
            oAuthEntity.setCreatedAt(LocalDateTime.now());
            oAuthEntity.setUserEntity(userEntity);

            System.out.println("service1 : " + oAuthTokenDto);
            oAuthRepository.save(oAuthEntity);
        } else {
            OAuthEntity oAuthEntity = new OAuthEntity();
            oAuthEntity.setId(oAuthTokenDto.getId());
            oAuthEntity.setUserName(oAuthTokenDto.getUserName());
            oAuthEntity.setProviderType(oAuthTokenDto.getProviderType());
            oAuthEntity.setAccessToken(oAuthTokenDto.getAccessToken());
            oAuthEntity.setTokenType(oAuthTokenDto.getTokenType());
            oAuthEntity.setExpiresIn(oAuthTokenDto.getExpiresIn());
            oAuthEntity.setRefreshToken(oAuthTokenDto.getRefreshToken());
            oAuthEntity.setCreatedAt(LocalDateTime.now());
            oAuthEntity.setScope(oAuthTokenDto.getScope());
            oAuthEntity.setEmail(oAuthTokenDto.getEmail());
            oAuthEntity.setName(oAuthTokenDto.getName());
            oAuthEntity.setAvatarUrl(oAuthTokenDto.getAvatarUrl());
            oAuthEntity.setUserEntity(userEntity);

            System.out.println("service2 : " + oAuthTokenDto);
            oAuthRepository.save(oAuthEntity);
        }
    }

    /*
    * 토큰 만료 여부 체크
    * */
    public boolean tokenExpired(String email) {
        Optional<OAuthEntity> oAuthEntity = oAuthRepository.findByEmail(email);

        // 토큰이 존재한다면 만료 되었는지 확인
        if(oAuthEntity.isPresent()) {
            OAuthEntity oAuthEntity2 = oAuthEntity.get();

            LocalDateTime createTime = oAuthEntity2.getCreatedAt();
            int expiresIn = oAuthEntity2.getExpiresIn();

            // 현재 시간이 (토큰 생성 시간 + 유효기간)을 지났는지 확인
            return LocalDateTime.now().isAfter(createTime.plusSeconds(expiresIn));

        }
        // 토큰이 없으면 만료된 것으로 처리
        return true;
    }

    public OAuthTokenDto refreshToken(OAuthTokenDto tokenRequest) {
        try {
            // 1. 토큰이 만료되지 않고 아직 유효하다면
            if(!tokenExpired(tokenRequest.getEmail())) {
                // 0203 수정
                Optional<OAuthEntity> existingToken = oAuthRepository.findByEmail(tokenRequest.getEmail());
                if(existingToken.isPresent()) {
                    OAuthEntity entity = existingToken.get();
                    OAuthTokenDto dto = new OAuthTokenDto();
                    dto.setEmail(entity.getEmail());
                    dto.setAccessToken(entity.getAccessToken());
                    dto.setRefreshToken(entity.getRefreshToken());
                    dto.setTokenType(entity.getTokenType());
                    dto.setExpiresIn(entity.getExpiresIn());
                    dto.setScope(entity.getScope());
                    return dto;
                }
            }

            // 1. 토큰이 만료됐거나 없으면 API를 통해 토큰 갱신
            Map<String, String> token =  api.refreshAccessToken(tokenRequest);

            System.out.println("service0000 + " + token);
            // 2. DTO에 갱신된 토큰 정보 설정
            OAuthTokenDto dto = new OAuthTokenDto();
            dto.setAccessToken(token.get("access_token"));
            dto.setTokenType(token.get("token_type"));
            dto.setRefreshToken(token.get("refresh_token"));
            dto.setExpiresIn(Integer.valueOf(token.get("expires_in")));
            dto.setScope(token.get("scope"));
            dto.setCreatedAt(LocalDateTime.now());

            // 3. 갱신된 토큰 정보 DB에 저장
            Optional<OAuthEntity> oAuthEntity = oAuthRepository.findByEmail(tokenRequest.getEmail());
            if(oAuthEntity.isPresent()) {
                OAuthEntity oAuthEntity1 = oAuthEntity.get();
                oAuthEntity1.setAccessToken(dto.getAccessToken());
                oAuthEntity1.setTokenType(dto.getTokenType());
                oAuthEntity1.setRefreshToken(dto.getRefreshToken());
                oAuthEntity1.setExpiresIn(Integer.valueOf(dto.getExpiresIn()));
                oAuthEntity1.setCreatedAt(LocalDateTime.now());

                System.out.println("save전 : " + oAuthEntity1);
                System.out.println("oauthId : " + oAuthEntity1.getOauthId());
                oAuthRepository.save(oAuthEntity1);
                System.out.println("save후: " + oAuthEntity1);
            }

            System.out.println("refreshToken: " + dto);
            return dto;

        } catch (Exception e) {
            return null;

        }

    }
}

