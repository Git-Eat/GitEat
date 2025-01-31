package com.giteat.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "avatar_url", nullable = true) // 프로필 이미지는 optional
    private String avatarUrl;

    @Column(name = "mm_webhook", nullable = true) // webhook도 optional일 수 있음
    private String mmWebhook;

    // OAuthToken 과 1:1 매핑관계
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private OAuthTokenEntity oauthToken;

}
