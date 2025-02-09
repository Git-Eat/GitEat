package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reply")
@Getter
@Setter
public class ReplyEntity {
    @EmbeddedId
    private ReplyId id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "dis_id")
    private String disId;

    @Column(name ="content")
    private String content;

    @Column(name = "reply_type")
    private int replyType;

    @Column(name = "create_at")
    private String createAt;
}
