package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PR")
@Getter
@Setter
public class GitLabMergeRequestEntity {
    @Id
    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;

    private String title;

    private  String description;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "target_branch")
    private String targetBranch;

    @Column(name = "source_branch")
    private String souceBranch;

    @Column(name = "is_opened")
    private int isOpened;

    @Column(name = "base_sha")
    private String baseSha;

    @Column(name = "head_sha")
    private String headSha;

    @Column(name = "start_sha")
    private String startSha;
}
