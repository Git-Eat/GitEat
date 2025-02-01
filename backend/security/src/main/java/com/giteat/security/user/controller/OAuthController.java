package com.giteat.security.user.controller;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.service.CustomOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {
    private final CustomOAuthService oauthService;
    private final OAuthApi oauthApi;

    public OAuthController(CustomOAuthService oauthService, OAuthApi oauthApi) {
        this.oauthService = oauthService;
        this.oauthApi = oauthApi;
    }

    /*
    * gitlab oauth 로그인 url을 생성하는 엔드포인트
    *
    *  1. 클라이언트가 로그인 버튼 클릭
     * 2. 서버가 GitLab OAuth 인증 URL 생성
     * 3. 생성된 URL을 클라이언트에게 반환
     * 4. 클라이언트는 해당 URL로 리다이렉트되어 GitLab 로그인 페이지로 이동
    * */
    @GetMapping("/gitlab/login")
    public ResponseEntity<?> gitlabLogin() {
        return ResponseEntity.ok(oauthService.gitlabLogin());

    }

    /*
    * gitlab 사용자 정보 조회
    *
    * @param accessToken Authorization 헤더에 포함된 access token
    * @return GitLab 사용자 정보 (이름, 이메일 등)
    *
    * 1. 클라이언트가 access token을 헤더에 포함하여 요청
    * 2. 서버가 해당 access token으로 GitLab API 호출
    * 3. GitLab으로부터 인증된 사용자의 정보를 받아서 반환
    * */
    @GetMapping("/gitlab/user")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(oauthApi.getUserInfo(accessToken));
    }

    /*
    * gitlab 인증 콜백 처리 (Authorization code -> 토큰)
    * - Authorization code를 받아 access, refresh 토큰을 발급받음
    *
    * 1. GitLab 로그인 완료 후 authorization code를 포함하여 이 엔드포인트로 리다이렉트됨
    * 2. 받은 authorization code로 GitLab API에 토큰 발급 요청
    * 3. GitLab으로부터 access token과 refresh token을 발급받음
    * */
    @GetMapping("/gitlab/callback")
    public ResponseEntity<?> gitlabCallback(@RequestParam String code, @RequestParam String state) {
        return ResponseEntity.ok(oauthService.gitlabCallback(code));
    }

    /*
    * Access 토큰 갱신
    * @param refreshToken 토큰 갱신에 사용할 refresh token
    * @return 새로 발급받은 access token
    *
    * 1. 기존 access token 만료
    * 2. refresh token을 GitLab에 전송하여 새로운 access token 요청
    * 3. GitLab이 새로운 access token 발급
    *
    * */
    @PostMapping("/gitlab/refresh")
    public ResponseEntity<?> gitlabRefresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(oauthApi.refreshAccessToken(refreshToken));
    }

//    @PostMapping("/gitlab/logout")
//    public ResponseEntity<Void> gitlabLogout(@RequestHeader("Authorization") String accessToken) {
//        oauthService.gitlabLogout(accessToken);
//        return ResponseEntity.ok().build();
//    }


    /**
     * GitHub 로그인 요청을 처리합니다.
     * 클라이언트가 이 엔드포인트를 호출하면 GitHub OAuth 인증 페이지로 리다이렉트됩니다.
     */
//    @GetMapping("github")




}
