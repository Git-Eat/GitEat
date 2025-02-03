package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Comment")
@Getter
@Setter
public class GitLabNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commnetId;

    @Column(name = "pr_id")
    private int prId;

    private String content;

    @Column(name = "comment_typle")
    private String commentType;

    private int depth;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "create_at")
    private String createAt;
}
