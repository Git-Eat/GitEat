package com.giteat.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "at_expires")
    private LocalDateTime atExpires;

    @Column(name = "rt_expires")
    private LocalDateTime rtExpires;

    @Column(name = "is_valid")
    private Boolean isValid;

    @OneToOne
    @JoinColumn(name = "oauth_id", nullable = false)
    private OAuthEntity oauthEntity;
}
