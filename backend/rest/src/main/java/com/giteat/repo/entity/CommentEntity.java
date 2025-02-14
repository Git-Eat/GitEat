package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {
    @EmbeddedId
    private CommentId id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "comment_type")
    private int commentType;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "dis_id")
    private String disId;

    @Column(name = "create_at")
    private String createAt;

    @Column(name="file_id")
    private String fileId;

    @Column(name = "new_line")
    private int newLine;

    @Column(name = "old_line")
    private int oldLine;

    @Column(name = "new_start_line")
    private int newStartLine;

    @Column(name = "new_end_line")
    private int newEndLine;

    @Column(name = "old_start_line")
    private int oldStartLine;

    @Column(name = "old_end_line")
    private int oldEndLine;

    @Column(name ="comment_value")
    private int commentValue;
}
