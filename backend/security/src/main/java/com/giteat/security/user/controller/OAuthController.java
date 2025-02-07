package com.giteat.security.user.controller;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {
    private final CustomOAuthService oauthService;
    private final ApiUtil apiUtil;

    public OAuthController(CustomOAuthService oauthService, OAuthApi oauthApi, ApiUtil apiUtil) {
        this.oauthService = oauthService;
        this.apiUtil = apiUtil;
    }

    /**
     * GitLab OAuth 로그인 처리 엔드포인트
     * 프로세스:
     * 1. 클라이언트가 로그인 버튼 클릭
     * 2. 서버가 GitLab OAuth 인증 URL 생성
     * 3. 생성된 URL을 클라이언트에게 반환
     * 4. 클라이언트는 해당 URL로 리다이렉트되어 GitLab 로그인 페이지로 이동
     *
     * @param body Authorization Code를 포함한 요청 본문
     * @return OAuth 토큰 정보
     */
    @PostMapping("/gitlab/login")
    @Operation(summary = "사용자 로그인", description = "gitLab ouath2 로그인")
    public ResponseEntity<?> gitlabLogin(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        OAuthTokenDto oAuthTokenDto = oauthService.gitlabLogin(code);
        ResponseEntity<?> testResponse = apiUtil.postApi("/oauth/gitlab", oAuthTokenDto , null);

        return ResponseEntity.ok(testResponse.getBody());
    }
    /**
     * GitLab OAuth 토큰 갱신 엔드포인트
     *
     * @param tokenRequest 갱신할 토큰 정보를 담은 DTO
     * @return 갱신된 토큰 정보
     */
    @PostMapping("/gitlab/refresh")
    @Operation(summary = "access 재발급", description = "refresh토큰으로 access토큰을 재발급 받을때 사용")
    public ResponseEntity<?> gitlabRefresh(@RequestBody OAuthTokenDto tokenRequest){
       return apiUtil.postApi("/oauth/refresh", tokenRequest , null);
    }

    /**
     * GitLab OAuth 일반 로그아웃 처리
     * Access Token을 무효화하고 현재 세션을 종료
     *
     * @param accessToken OAuth 토큰 정보를 담고 있는 DTO
     * @return 로그아웃 처리 결과
     */
    @PostMapping("/gitlab/logout")
    @Operation(summary = "사용자 로그아웃", description = "아마 안써도 될듯?")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok().build();
    }

    /**
     * accessToken으로 사용자 정보를 가져오는 함수
     * @param token
     * @return
     */
    @GetMapping("/gitlab/userinfo")
    @Operation(summary = "사용자 정보", description = "accessToken을 기반으로 사용자 정보를 가져옴")
    public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String token) {
        String accessToken = token.split(" ")[1];
        return ResponseEntity.ok().body(oauthService.getMyInfo(accessToken));
    }

}
