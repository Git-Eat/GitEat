package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PR")
@Getter
@Setter
public class MergeRequestEntity {

    @EmbeddedId
    private MergeRequestId id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private  String description;

    @Column(name ="user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_profile")
    private String userProfile;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "target_branch")
    private String targetBranch;

    @Column(name = "source_branch")
    private String sourceBranch;

    @Column(name = "is_opened")
    private int isOpened;

    @Column(name = "base_sha")
    private String baseSha;

    @Column(name = "head_sha")
    private String headSha;

    @Column(name = "start_sha")
    private String startSha;
}
