package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommitId implements Serializable {
    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "repo_id")
    private int repoId;

    @Column(name = "pr_id")
    private int prId;
}

