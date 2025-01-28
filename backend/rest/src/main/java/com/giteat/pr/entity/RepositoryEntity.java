package com.giteat.pr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="repository")
public class RepositoryEntity {

    @Id
    @Column(name="repo_id")
    private int id;

    private String userId;
    private String name;
    private String description;
    private String githubUrl;
    private String gitlabUrl;
    private LocalDateTime createAt;
}
