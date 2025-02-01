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
    private final OAuthApi oauthApi;

    private final ApiUtil apiUtil;

    public OAuthController(CustomOAuthService oauthService, OAuthApi oauthApi, ApiUtil apiUtil) {
        this.oauthService = oauthService;
        this.oauthApi = oauthApi;
        this.apiUtil = apiUtil;
    }

    /*
    * gitlab oauth 로그인 url을 생성하는 엔드포인트
    *
    *  1. 클라이언트가 로그인 버튼 클릭
     * 2. 서버가 GitLab OAuth 인증 URL 생성
     * 3. 생성된 URL을 클라이언트에게 반환
     * 4. 클라이언트는 해당 URL로 리다이렉트되어 GitLab 로그인 페이지로 이동
    * */
    @PostMapping("/gitlab/login")
    public ResponseEntity<?> gitlabLogin(@RequestBody Object body){
        String code = (String) body;

        OAuthTokenDto oAuthTokenDto =  oauthService.gitlabLogin(code);
        return ResponseEntity.ok(oAuthTokenDto);
    }



}
