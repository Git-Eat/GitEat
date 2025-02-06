package com.giteat.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "change_file")
@Getter
@Setter
public class FileChangeEntity {
    @Id
    @Column(name = "file_id")
    private String fileId;

    @Id
    @Column(name = "repo_id")
    private int repoId;

    @Id
    @Column(name = "pr_id")
    private int prId;

    @Id
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
