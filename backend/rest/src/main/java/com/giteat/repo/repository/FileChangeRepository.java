package com.giteat.repo.repository;

import com.giteat.repo.entity.FileChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileChangeRepository  extends JpaRepository<FileChangeEntity, Long> {
}
