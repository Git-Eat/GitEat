package com.giteat.pr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="reply")
@Getter
@Setter
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

    @Column(name="reply_type")
    private String replyType;

    @Column(name="image_name")
    private String imageName;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
