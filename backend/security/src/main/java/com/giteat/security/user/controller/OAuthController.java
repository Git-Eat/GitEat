package com.giteat.security.user.controller;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import com.giteat.security.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
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
    public ResponseEntity<?> gitlabLogin(@RequestBody Object body){
        String code = (String) body;
        OAuthTokenDto oAuthTokenDto = oauthService.gitlabLogin(code);
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

}
