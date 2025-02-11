package com.giteat.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false) // false
    private Integer userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "avatar_url", nullable = true) // 프로필 이미지는 optional
    private String avatarUrl;

    @Column(name = "user_name", nullable = false)
    private String userName;

    // OAuthToken 과 1:1 매핑관계
    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private OAuthEntity oAuthEntity;

}
