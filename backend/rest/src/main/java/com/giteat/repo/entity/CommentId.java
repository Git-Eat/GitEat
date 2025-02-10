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
public class CommentId implements Serializable {
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;
}

