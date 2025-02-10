package com.giteat.security.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OAuthTokenDto {
    private String code;

    private int userId;
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
    private String scope;
    private String createAt;
    private String email;
    private String name;
    private String userName;
    private String avatarUrl;


}
