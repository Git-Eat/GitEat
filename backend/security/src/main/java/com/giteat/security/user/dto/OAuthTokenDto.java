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
    private Integer Id; // gitlab에서 주는 id값
    private int userId;
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
    private String scope;
    private LocalDateTime createdAt;
    private String email;
    private String name;
    private String userName;
    private String avatarUrl;


}
