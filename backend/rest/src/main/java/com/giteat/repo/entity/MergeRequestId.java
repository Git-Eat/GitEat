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
public class MergeRequestId implements Serializable {
    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;
}