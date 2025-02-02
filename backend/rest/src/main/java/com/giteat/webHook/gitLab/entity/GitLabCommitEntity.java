package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Commit")
@Getter
@Setter
public class GitLabCommitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_id")
    private int commitId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repository_id")
    private int repositoryId;

    private String content;

    @Column(name = "commited_at")
    private String commitedAt;
}
