package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="repository")
@Getter
@Setter
public class RepositoryEntity {

    @Id
    @Column(name="repo_id")
    private int repoId;

    @Column(name="user_id")
    private int userId;
    private String name;
    private String description;

    @Column(name="github_url")
    private String githubUrl;

    @Column(name="gitlab_url")
    private String gitlabUrl;

    @Column(name="create_at")
    private LocalDateTime createAt;

}
