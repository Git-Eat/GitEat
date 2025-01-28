package com.giteat.pr.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="pr")
public class PrEntity {
    @Id
    @Column(name="pr_id")
    private int id;

    private int userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String target_branch;
    private String source_branch;
    private int is_opened;

    @OneToMany(mappedBy = "pr")
    private List<CommitEntity> commits;

    @OneToMany(mappedBy = "pr")
    private List<CommentEntity> comments;
}
