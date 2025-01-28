package com.giteat.pr.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comment")
public class CommentEntity {

    @Id
    @Column(name="comment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="pr_id")
    private PrEntity pr;

    private String content;
    private String commentType;
    private int depth;
    private String image_name;
    private LocalDateTime createdAt;
}
