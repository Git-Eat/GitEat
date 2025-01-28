package com.giteat.pr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    @Column(name="comment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="pr_id")
    private PrEntity pr;

    private String content;

    @Column(name="comment_type")
    private String commentType;
    private int depth;
    private String image_name;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
