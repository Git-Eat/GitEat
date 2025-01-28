package com.giteat.security.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OAuthToken {
    private Integer oauthId;
    private Integer userId;
    private String providerType;
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String scope;
    private String email;
    private String name;
    private String avatarUrl;
    private String createAt;
}
