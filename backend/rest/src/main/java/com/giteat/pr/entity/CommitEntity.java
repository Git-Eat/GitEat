package com.giteat.pr.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="commit")
public class CommitEntity {

    @Id
    @Column(name="commit_id")
    private String id;

    private String content;
    private LocalDateTime commitedAt;

    @ManyToOne
    @JoinColumn(name="pr_id")
    private PrEntity pr;
}
