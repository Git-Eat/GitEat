package com.giteat.security.user.controller;

import com.giteat.security.user.api.OAuthApi;
import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.service.CustomOAuthService;
import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> gitlabLogin(@RequestBody Map<String, String> body , HttpServletResponse response) {
        String code = body.get("code");
        OAuthTokenDto oAuthTokenDto = oauthService.gitlabLogin(code , response);
        ResponseEntity<?> testResponse = apiUtil.postApi("/oauth/gitlab", oAuthTokenDto , oAuthTokenDto.getAccessToken());
        oauthService.createCookieAndToken(oAuthTokenDto.getAccessToken() ,oAuthTokenDto.getRefreshToken() , response);
        return ResponseEntity.ok(testResponse.getStatusCode());
    }


    /**
     *  refresh 재발급
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/gitlab/refresh")
    @Operation(summary = "토큰 재발급", description = "refresh토큰으로 모든 토큰 재발급")
    public ResponseEntity<?> gitlabRefresh(HttpServletRequest request , HttpServletResponse response) {
        log.info("refresh 재발급 도착");
        int tokenResult = oauthService.createNewTokens(request, response);
        if(tokenResult==0){
            return ResponseEntity.ok("fail");
        }else{
            return ResponseEntity.ok("success");
        }
    }


    /**
     * GitLab OAuth 일반 로그아웃 처리
     * Access Token을 무효화하고 현재 세션을 종료
     *
     *
     * @return 로그아웃 처리 결과
     */
    @PostMapping("/gitlab/logout")
    @Operation(summary = "사용자 로그아웃", description = "아마 안써도 될듯?")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    /**
     * accessToken으로 사용자 정보를 가져오는 함수
     * @return
     */
    @GetMapping("/gitlab/userinfo")
    @Operation(summary = "사용자 정보", description = "accessToken을 기반으로 사용자 정보를 가져옴")
    public ResponseEntity<?> getMyInfo() {
        return ResponseEntity.ok().body(oauthService.getMyInfo());
    }

}
