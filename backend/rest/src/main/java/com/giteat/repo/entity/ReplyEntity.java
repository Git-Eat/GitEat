package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reply")
@Getter
@Setter
public class ReplyEntity {
    @Id
    @Column(name = "re_comment_id")
    private int reCommentId;

    @Column(name = "repo_id")
    private int repoId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "dis_id")
    private String disId;

    @Column(name ="content")
    private String content;

    @Column(name = "reply_type")
    private int replyType;

    @Column(name = "create_at")
    private Date createAt;
}
