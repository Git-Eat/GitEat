package com.giteat.pr.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="reply")
public class ReplyEntity {

    @Id
    @Column(name="re_comment_id")
    private int reCommentId;

    @ManyToOne
    @JoinColumn(name="comment_id", nullable = false)
    private CommentEntity comment;

//    @ManyToOne
//    @JoinColumn(name="ai_comment_id")
//    private CommentEntity aiComment;

    private String content;
    private String replyType;
    private String imageName;
    private LocalDateTime createdAt;
}
