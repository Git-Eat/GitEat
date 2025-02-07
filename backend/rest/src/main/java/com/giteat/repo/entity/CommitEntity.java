package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Commit")
@Getter
@Setter
public class CommitEntity {
    @Id
    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repositoryId;

    private String content;

    @Column(name = "commited_at")
    private String commitedAt;
}
