package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PR")
@Getter
@Setter
public class MergeRequestEntity {
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
