package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitLabNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID (필요 시)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "pr_id", nullable = false)
    private int prId;

    @Column(name = "repo_id", nullable = false)
    private int repoId;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "comment_type")
    private Integer commentType;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "dis_id", length = 255)
    private String disId;

    @Column(name = "image_name", length = 255)
    private String imageName;

    @Column(name = "create_at", length = 255)
    private String createAt;

    @Column(name = "comment_typle", length = 255) // 오타 주의
    private String commentTyple;

    @Column(name = "depth", nullable = false)
    private int depth;
}