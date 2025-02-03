package com.giteat.webHook.gitLab.repository;

import com.giteat.webHook.gitLab.entity.GitLabCommitEntity;
import com.giteat.webHook.gitLab.entity.GitLabNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitLabNoteRepository extends JpaRepository<GitLabNoteEntity, Long> {
}
