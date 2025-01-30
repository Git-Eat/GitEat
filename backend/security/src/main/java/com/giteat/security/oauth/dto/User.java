package com.giteat.security.oauth.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private Integer userId;
    private String email;
    private String name;
    private String avatarUrl;
    private String mmWebhook;
}
