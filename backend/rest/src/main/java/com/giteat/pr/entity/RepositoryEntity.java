package com.giteat.pr.entity;

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
    private int id;

    private int userId;
    private String name;
    private String description;
    private String githubUrl;
    private String gitlabUrl;
    private LocalDateTime createAt;
}
