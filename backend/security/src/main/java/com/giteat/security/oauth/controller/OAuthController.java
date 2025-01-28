package com.giteat.security.oauth.controller;

import com.giteat.security.oauth.service.CustomOAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private CustomOAuthService customOAuthService;

    /**
     * GitLab 로그인 요청을 처리합니다.
     * 클라이언트가 이 엔드포인트를 호출하면 GitLab OAuth 인증 페이지로 리다이렉트됩니다.
     */
    @GetMapping("gitlab")
    public void gitlabLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/gitlab");
    }

    /**
     * GitHub 로그인 요청을 처리합니다.
     * 클라이언트가 이 엔드포인트를 호출하면 GitHub OAuth 인증 페이지로 리다이렉트됩니다.
     */
//    @GetMapping("github")
//    public void githubLogin(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/oauth2/authorization/github");
//    }


      //security config에서 설정
//    @PostMapping("logout")
//    public ResponseEntity<String> logout(){
//        return ResponseEntity.ok("logout");
//    }

}
