package com.giteat.webHook.gitLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "change_file")
//@Getter
//@Setter
public class GitLabFileChangeEntity {
    @Id
    @Column(name = "file_id")
    private String fileId;

    @Column(name = "repo_id")
    private int repoId;

    @Column(name = "pr_id")
    private int prId;

    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "old_path")
    private String oldPath;

    @Column(name = "new_path")
    private String newPath;

    @Column(name = "file_status")
    private int fileStatus;

}
