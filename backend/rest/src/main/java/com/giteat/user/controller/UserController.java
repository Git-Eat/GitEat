package com.giteat.user.controller;

import com.giteat.user.dto.OAuthTokenDto;
import com.giteat.user.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class UserController {

    private final OAuthService oAuthService;

    public UserController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    /**
     * GitLab OAuth 토큰을 저장하는 엔드포인트
     * 클라이언트로부터 받은 OAuth 토큰 정보를 저장
     *
     * @param oAuthTokenDto OAuth 토큰 정보를 담고 있는 DTO
     * @return 저장된 토큰 정보
     */
    @PostMapping("/gitlab")
    public ResponseEntity<?> saveToken(@RequestBody OAuthTokenDto oAuthTokenDto) {
//        oAuthService.saveToken(oAuthTokenDto);
//        System.out.println("rest controller 토큰정보: "+ oAuthTokenDto);
//        return ResponseEntity.ok(oAuthTokenDto);
        try {
            System.out.println("gitLab 도착함");
            oAuthService.saveToken(oAuthTokenDto);
            return ResponseEntity.ok().body(oAuthTokenDto);
        } catch (Exception e) {
            log.error("Token 저장 중 오류 발생: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * OAuth 토큰을 갱신하는 엔드포인트
     * 만료된 토큰을 새로운 토큰으로 갱신
     *
     * @param tokenRequest 갱신이 필요한 토큰 정보를 담고 있는 DTO
     * @return 갱신된 새로운 토큰 정보
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody OAuthTokenDto tokenRequest) {
        OAuthTokenDto newToken = oAuthService.refreshToken(tokenRequest);

        return ResponseEntity.ok(newToken);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
//        return ResponseEntity.ok().build();
//    }
}




