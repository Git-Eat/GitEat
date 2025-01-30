package com.giteat.pr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="commit")
@Getter
@Setter
public class CommitEntity {

    @Id
    @Column(name="commit_id")
    private String commitId;

    private String content;

    @Column(name="commited_at")
    private LocalDateTime commitedAt;

    @ManyToOne
    @JoinColumn(name="pr_id")
    private PrEntity pr;
}
