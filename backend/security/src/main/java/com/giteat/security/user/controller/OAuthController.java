package com.giteat.security.user.controller;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import com.giteat.security.util.ApiUtil;
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
     * @param  body Code를 포함한 요청 본문
     * @return OAuth 토큰 정보
     */
    @PostMapping("/gitlab/login")
    public ResponseEntity<?> gitlabLogin(@RequestBody Map<String, String> body) {
        System.out.println("클라이언트에서 받은 body값:" + body);
        String code = body.get("code");
        System.out.println("code:" + code);

        OAuthTokenDto oAuthTokenDto = oauthService.gitlabLogin(code);
        System.out.println("security dto: "+ oAuthTokenDto);

        return apiUtil.postApi("/oauth/gitlab", oAuthTokenDto);
    }
    /**
     * GitLab OAuth 토큰 갱신 엔드포인트
     *
     * @param tokenRequest 갱신할 토큰 정보를 담은 DTO
     * @return 갱신된 토큰 정보
     */
    @PostMapping("/gitlab/refresh")
    public ResponseEntity<?> gitlabRefresh(@RequestBody OAuthTokenDto tokenRequest){
       return apiUtil.postApi("/oauth/refresh", tokenRequest);
    }

    /**
     * GitLab OAuth 일반 로그아웃 처리
     * Access Token을 무효화하고 현재 세션을 종료
     *
     * @param accessToken OAuth 토큰 정보를 담고 있는 DTO
     * @return 로그아웃 처리 결과
     */
    @PostMapping("/gitlab/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok().build();
    }


//    /**
//     * GitLab 계정 연동 해제
//     * GitLab 계정과 애플리케이션의 연동을 완전히 해제
//     *
//     * @param oAuthTokenDto OAuth 토큰 정보를 담고 있는 DTO
//     * @return 연동 해제 처리 결과
//     */
//    @PostMapping("/gitlab/revoke")
//    public ResponseEntity<?> gitlabUnlink(@RequestBody OAuthTokenDto oAuthTokenDto){
//
//        return apiUtil.postFormApi("/oauth/revoke", oAuthTokenDto);
//    }
}
