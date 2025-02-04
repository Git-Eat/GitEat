package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class GitLabCommentEntity {

    @Id
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;

    private String content;

    @Column(name = "comment_type")
    private int commentType;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "dis_id")
    private String disId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "create_at")
    private String createAt;
}
