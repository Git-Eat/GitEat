package com.giteat.user.controller;

import com.giteat.user.model.dto.OAuthTokenDto;
import com.giteat.user.model.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class UserController {

    private final OAuthService oAuthService;

    public UserController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/gitlab")
    public ResponseEntity<?> saveToken(@RequestBody OAuthTokenDto oAuthTokenDto) {
        System.out.println("dto : " +oAuthTokenDto);
        oAuthService.saveToken(oAuthTokenDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody OAuthTokenDto tokenRequest) {
        System.out.println("도착했어요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        System.out.println("refreshToken dto : " +tokenRequest);
        OAuthTokenDto newToken = oAuthService.refreshToken(tokenRequest);
        return ResponseEntity.ok(newToken);
    }


}


