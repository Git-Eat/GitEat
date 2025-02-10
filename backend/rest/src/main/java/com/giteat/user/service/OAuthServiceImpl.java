package com.giteat.user.service;

import com.giteat.common.GitLabApi;
import com.giteat.user.entity.UserEntity;
import com.giteat.user.repository.UserRepository;
import com.giteat.user.dto.OAuthTokenDto;
import com.giteat.user.entity.OAuthEntity;
import com.giteat.user.repository.OAuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * OAuth 인증 관련 비즈니스 로직을 처리하는 서비스 구현체
 * GitLab OAuth 토큰 관리 및 사용자 정보 관리 기능 제공
 */
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

    /**
     * OAuth 토큰 정보 저장 및 사용자 정보 관리
     * 신규 사용자인 경우 사용자 정보를 새로 저장하고, 기존 사용자인 경우 토큰 정보만 업데이트
     *
     * @param oAuthTokenDto 저장할 OAuth 토큰 및 사용자 정보를 담고 있는 DTO
     */
    public void saveToken(OAuthTokenDto oAuthTokenDto) {
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

            oAuthRepository.save(oAuthEntity);
        } else {
            OAuthEntity oAuthEntity = new OAuthEntity();
            oAuthEntity.setId(oAuthTokenDto.getUserId());
            oAuthEntity.setUserName(oAuthTokenDto.getUserName());
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

            oAuthRepository.save(oAuthEntity);
        }
    }

    /**
     * OAuth 토큰의 만료 여부를 확인
     * 토큰 생성 시간과 유효 기간을 기준으로 만료 여부 판단
     *
     * @param email 확인할 사용자의 이메일
     * @return true: 토큰 만료됨, false: 토큰 유효함
     */
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
    /**
     * OAuth 토큰 갱신
     * 기존 토큰이 만료된 경우 GitLab API를 통해 새로운 토큰을 발급받음
     * 토큰이 유효한 경우 기존 토큰을 반환
     *
     * @param tokenRequest 갱신할 토큰 정보를 담고 있는 DTO
     * @return 갱신된 토큰 정보 또는 유효한 현재 토큰 정보, 실패 시 null
     */
    public OAuthTokenDto refreshToken(OAuthTokenDto tokenRequest) {
        try {
            // refreshToken으로 기존 토큰 정보 조회
            Optional<OAuthEntity> existingToken = oAuthRepository.findByRefreshToken(tokenRequest.getRefreshToken());

            // DB에 토큰이 있는지 확인
            if(existingToken.isPresent()) {
                OAuthEntity entity = existingToken.get();

                // 1. 토큰이 만료되지 않고 아직 유효하다면 현재 토큰 반환
                if(!tokenExpired(tokenRequest.getEmail())) {
                    OAuthTokenDto dto = new OAuthTokenDto();
                    dto.setEmail(entity.getEmail());
                    dto.setAccessToken(entity.getAccessToken());
                    dto.setRefreshToken(entity.getRefreshToken());
                    dto.setTokenType(entity.getTokenType());
                    dto.setExpiresIn(entity.getExpiresIn());
                    dto.setScope(entity.getScope());
                    return dto;
                }

            // 2. 토큰이 만료됐거나 없으면 API를 통해 토큰 갱신
            Map<String, String> token =  api.refreshAccessToken(tokenRequest);
            if(token == null) { return null; }

            // 3. DTO에 갱신된 토큰 정보 설정
            OAuthTokenDto dto = new OAuthTokenDto();

            dto.setEmail(entity.getEmail()); // 기존 entity에서 email 가져오기
            dto.setAccessToken(token.get("access_token"));
            dto.setTokenType(token.get("token_type"));
            dto.setRefreshToken(token.get("refresh_token"));
            dto.setExpiresIn(Integer.valueOf(token.get("expires_in")));
            dto.setScope(token.get("scope"));
            dto.setCreatedAt(LocalDateTime.now());

            // 4. 갱신된 토큰 정보 DB에 저장
            entity.setAccessToken(dto.getAccessToken());
            entity.setTokenType(dto.getTokenType());
            entity.setRefreshToken(dto.getRefreshToken());
            entity.setExpiresIn(dto.getExpiresIn());
            entity.setScope(dto.getScope());
            entity.setCreatedAt(LocalDateTime.now());

            oAuthRepository.save(entity);
            return dto;
        }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public void logout(OAuthTokenDto oAuthTokenDto) {
//        try {
//            Optional<OAuthEntity> existOAuth = oAuthRepository.findByEmail(oAuthTokenDto.getEmail());
//            if(existOAuth.isPresent()) {
//                OAuthEntity entity = existOAuth.get();
//                // accessToken만 null로 설정하고 나머지 정보는 유지
//                entity.setAccessToken(null);
//                oAuthRepository.save(entity);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException("Failed to logout user",e);
//        }
//    }
}

