package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UsersEntity {

    @Id
    @Column(name = "user_id", nullable = false) // false
    private Integer userId;

    @Column
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name = "avatar_url", nullable = true) // 프로필 이미지는 optional
    private String avatarUrl;

    @Column(name = "mm_webhook", nullable = true) // webhook도 optional일 수 있음
    private String mmWebhook;
}
