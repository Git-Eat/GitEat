package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Commit")
@Getter
@Setter
public class CommitEntity {

    @EmbeddedId
    private CommitId id;

    private String content;

    @Column(name = "commited_at")
    private String commitedAt;
}
