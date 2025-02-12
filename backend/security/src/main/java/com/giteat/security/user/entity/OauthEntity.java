package com.giteat.security.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth_token")
@Getter
@Setter
@AllArgsConstructor
public class OauthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id") // oauth_id 컬럼명과 매핑
    private Long oauthId;

    @Column(name = "id") // id 컬럼명과 매핑
    private Long id;

    @Column(name = "user_name") // user_name 컬럼명과 매핑
    private String userName;

    @Column(name = "provider_type") // provider_type 컬럼명과 매핑
    private String providerType;

    @Column(name = "access_token") // access_token 컬럼명과 매핑
    private String accessToken;

    @Column(name = "token_type") // token_type 컬럼명과 매핑
    private String tokenType;

    @Column(name = "expires_in") // expires_in 컬럼명과 매핑
    private Integer expiresIn;

    @Column(name = "refresh_token") // refresh_token 컬럼명과 매핑
    private String refreshToken;

    @Column(name = "created_at") // created_at 컬럼명과 매핑
    private LocalDateTime createdAt;

    @Column(name = "scope") // scope 컬럼명과 매핑
    private String scope;

    @Column(name = "email") // email 컬럼명과 매핑
    private String email;

    @Column(name = "name") // name 컬럼명과 매핑
    private String name;

    @Column(name = "avatar_url") // avatar_url 컬럼명과 매핑
    private String avatarUrl;

    public OauthEntity() {

    }
}
