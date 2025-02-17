package com.giteat.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth_token")
@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성
public class OAuthEntity {

    @Version
    private Integer version;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "token_type", nullable = false)
    private String tokenType;

    // GitLab만 제공하는 필드
    @Column(name = "expires_in", nullable = true)
    private Integer expiresIn;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    // GitHub만 제공하는 필드
    @Column(nullable = true)
    private String scope;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

//    @OneToOne(mappedBy = "oauthToken", cascade = CascadeType.ALL)
//    private TokenEntity token;


}
