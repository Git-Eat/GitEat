package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {
    @Id
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "repo_id")
    private int repoId;

    private String content;

    @Column(name = "comment_type")
    private int commentType;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "dis_id")
    private String disId;

    @Column(name = "create_at")
    private String createAt;

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
}
