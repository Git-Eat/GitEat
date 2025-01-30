package com.giteat.pr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="pr")
@Getter
@Setter
public class PrEntity {
    @Id
    @Column(name="pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;
    private String title;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "target_branch")
    private String targetBranch;

    @Column(name = "source_branch")
    private String sourceBranch;

    @Column(name = "is_opened")
    private int isOpened;

//    @OneToMany(mappedBy = "pr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<CommitEntity> commits;
//
//    @OneToMany(mappedBy = "pr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<CommentEntity> comments;
}
