package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "repository")
//@Getter
//@Setter
public class GitLabRepositoryEntity {

    @Id
    @Column(name = "repo_id")
    private int repoId;

    private String name;

    private String description;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "gitlab_url")
    private String gitlabUrl;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "user_id")
    private String userId;

}
