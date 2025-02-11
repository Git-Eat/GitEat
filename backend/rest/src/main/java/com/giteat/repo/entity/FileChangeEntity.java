package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "change_file")
@Getter
@Setter
public class FileChangeEntity {
    @EmbeddedId
    private FileChangeId id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "old_path")
    private String oldPath;

    @Column(name = "new_path")
    private String newPath;

    @Column(name = "file_status")
    private int fileStatus;
}
